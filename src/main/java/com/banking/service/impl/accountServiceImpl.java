package com.banking.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.dto.AccountDto;
import com.banking.entities.Account;
import com.banking.entities.AccountStatus;
import com.banking.entities.Customer;
import com.banking.exceptions.BadRequestException;
import com.banking.exceptions.ResourceNotFoundException;
import com.banking.mapper.AccountMapper;
import com.banking.repostories.AccountRepository;
import com.banking.repostories.CustomerRepository;
import com.banking.service.AccountService;
import com.banking.service.TransactionService;
import com.banking.entities.TransactionType;
import com.banking.entities.TransactionChannel;

@Service
public class accountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionService transactionService;

    public accountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionService = transactionService;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        if (account.getAccountNumber() == null || account.getAccountNumber().trim().isEmpty()) {
            throw new BadRequestException("Account number is required");
        }
        if (accountRepository.findByAccountNumber(account.getAccountNumber()).isPresent()) {
            throw new BadRequestException("Account number already exists: " + account.getAccountNumber());
        }

        String customerNumber = accountDto.getCustomerNumber();
        if (customerNumber == null || customerNumber.trim().isEmpty()) {
            throw new BadRequestException("Customer number is required to create an account");
        }

        Customer customer = customerRepository.findByCustomerNumber(customerNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with customer number: " + customerNumber));

        account.setCustomer(customer);
        customer.addAccount(account);

        Account saved = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(saved);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto getAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with number: " + accountNumber));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findByCustomerCustomerId(customerId).stream()
                .map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccountDto deposit(Long id, BigDecimal amount) {
        // delegate to transaction service to ensure transaction record is created atomically
        transactionService.createTransaction(id, TransactionType.DEPOSIT, amount, "Deposit via API", TransactionChannel.SYSTEM, null);
        Account account = findEntityById(id);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    @Transactional
    public AccountDto withdraw(Long id, BigDecimal amount) {
        // delegate to transaction service which enforces balance checks
        transactionService.createTransaction(id, TransactionType.WITHDRAWAL, amount, "Withdrawal via API", TransactionChannel.SYSTEM, null);
        Account account = findEntityById(id);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto blockAccount(Long id) {
        Account account = findEntityById(id);
        account.setStatus(AccountStatus.BLOCKED);
        return AccountMapper.mapToAccountDto(accountRepository.save(account));
    }

    @Override
    public AccountDto closeAccount(Long id) {
        Account account = findEntityById(id);
        account.setStatus(AccountStatus.CLOSED);
        return AccountMapper.mapToAccountDto(accountRepository.save(account));
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    public Account findEntityById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }
}

