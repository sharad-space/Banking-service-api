package com.banking.dto;

import java.util.List;

public class CustomerBankingDetailDto {

    private Long customerId;
    private CustomerDto customer;
    private List<CustomerBankingAccountDetailDto> accounts;

    public CustomerBankingDetailDto() {}

    public CustomerBankingDetailDto(Long customerId, CustomerDto customer, List<CustomerBankingAccountDetailDto> accounts) {
        this.customerId = customerId;
        this.customer = customer;
        this.accounts = accounts;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public List<CustomerBankingAccountDetailDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<CustomerBankingAccountDetailDto> accounts) {
        this.accounts = accounts;
    }
}
