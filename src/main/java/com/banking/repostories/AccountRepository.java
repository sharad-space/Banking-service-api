package com.banking.repostories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByCustomerCustomerId(Long customerId);

    List<Account> findByCustomerCustomerNumber(String customerNumber);
}
