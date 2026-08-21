package com.banking.mapper;

import com.banking.dto.AccountDto;
import com.banking.entities.Account;
import com.banking.entities.AccountStatus;
import com.banking.entities.AccountType;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setAccountId(accountDto.getId());
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setBalance(accountDto.getBalance() == null ? java.math.BigDecimal.ZERO : accountDto.getBalance());
        account.setCurrency(accountDto.getCurrency() == null ? "INR" : accountDto.getCurrency());
        account.setMinimumBalance(accountDto.getMinimumBalance() == null ? java.math.BigDecimal.ZERO : accountDto.getMinimumBalance());
        if (accountDto.getAccountType() != null && !accountDto.getAccountType().trim().isEmpty()) {
            account.setAccountType(AccountType.valueOf(accountDto.getAccountType()));
        }
        if (accountDto.getStatus() != null && !accountDto.getStatus().trim().isEmpty()) {
            account.setStatus(AccountStatus.valueOf(accountDto.getStatus()));
        }
        return account;
    }

    public static AccountDto mapToAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getAccountId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setBalance(account.getBalance());
        accountDto.setCurrency(account.getCurrency());
        accountDto.setMinimumBalance(account.getMinimumBalance());
        accountDto.setAccountType(account.getAccountType() == null ? null : account.getAccountType().name());
        accountDto.setStatus(account.getStatus() == null ? null : account.getStatus().name());
        if (account.getCustomer() != null) {
            accountDto.setCustomerNumber(account.getCustomer().getCustomerNumber());
            accountDto.setAccountHolderName(account.getCustomer().getFirstName() + " " + account.getCustomer().getLastName());
        }
        return accountDto;
    }
}
