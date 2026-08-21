package com.banking.service;

import java.math.BigDecimal;
import java.util.List;

import com.banking.dto.AccountDto;
import com.banking.entities.Account;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto getAccountByAccountNumber(String accountNumber);

    List<AccountDto> getAccountsByCustomerId(Long customerId);

    AccountDto deposit(Long id, BigDecimal amount);

    AccountDto withdraw(Long id, BigDecimal amount);

    AccountDto blockAccount(Long id);

    AccountDto closeAccount(Long id);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);

    Account findEntityById(Long id);
}
 