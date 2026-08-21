package com.banking.dto;

import java.util.List;

public class CustomerBankingAccountDetailDto {

    private AccountDto account;
    private List<TransactionResponse> transactions;

    public CustomerBankingAccountDetailDto() {}

    public CustomerBankingAccountDetailDto(AccountDto account, List<TransactionResponse> transactions) {
        this.account = account;
        this.transactions = transactions;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }
}
