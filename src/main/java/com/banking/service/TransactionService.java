package com.banking.service;

import java.math.BigDecimal;
import java.util.List;

import com.banking.entities.Transaction;
import com.banking.entities.TransactionChannel;
import com.banking.entities.TransactionType;

public interface TransactionService {

    Transaction createTransaction(Long accountId, TransactionType transactionType, BigDecimal amount,
                                 String description, TransactionChannel channel, String referenceNumber);

    Transaction getTransactionById(Long transactionId);

    Transaction getTransactionByReference(String transactionReference);

    List<Transaction> getTransactionsByAccountId(Long accountId);
}
