package com.banking.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.entities.TransactionChannel;
import com.banking.entities.TransactionStatus;
import com.banking.entities.TransactionType;
import com.banking.exceptions.BadRequestException;
import com.banking.exceptions.ResourceNotFoundException;
import com.banking.repostories.AccountRepository;
import com.banking.repostories.TransactionRepository;
import com.banking.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Transaction createTransaction(Long accountId, TransactionType transactionType, BigDecimal amount,
                                        String description, TransactionChannel channel, String referenceNumber) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Transaction amount must be greater than zero");
        }

        BigDecimal balanceBefore = account.getBalance() == null ? BigDecimal.ZERO : account.getBalance();
        BigDecimal balanceAfter = balanceBefore;

        if (transactionType == TransactionType.DEPOSIT) {
            balanceAfter = balanceBefore.add(amount);
            account.setBalance(balanceAfter);
        } else if (transactionType == TransactionType.WITHDRAWAL || transactionType == TransactionType.ATM_WITHDRAWAL
                || transactionType == TransactionType.UPI_PAYMENT || transactionType == TransactionType.NEFT
                || transactionType == TransactionType.RTGS || transactionType == TransactionType.IMPS) {
            if (balanceBefore.compareTo(amount) < 0) {
                throw new BadRequestException("Insufficient balance");
            }
            balanceAfter = balanceBefore.subtract(amount);
            account.setBalance(balanceAfter);
        }

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        transaction.setBalanceBefore(balanceBefore);
        transaction.setBalanceAfter(balanceAfter);
        transaction.setDescription(description);
        transaction.setReferenceNumber(referenceNumber);
        transaction.setChannel(channel);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionReference(UUID.randomUUID().toString());
        transaction.setStatus(TransactionStatus.SUCCESS);

        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));
    }

    @Override
    public Transaction getTransactionByReference(String transactionReference) {
        return transactionRepository.findByTransactionReference(transactionReference)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with reference: " + transactionReference));
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountAccountId(accountId);
    }
}
