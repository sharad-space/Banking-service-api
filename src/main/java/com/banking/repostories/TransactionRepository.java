package com.banking.repostories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionReference(String transactionReference);

    List<Transaction> findByAccountAccountId(Long accountId);

    List<Transaction> findByAccountCustomerCustomerId(Long customerId);
}