package com.banking.dto;

import java.math.BigDecimal;

import com.banking.entities.TransactionChannel;
import com.banking.entities.TransactionType;

import jakarta.validation.constraints.NotNull;

public class TransactionRequest {

    @NotNull
    private Long accountId;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private BigDecimal amount;

    private String description;
    private TransactionChannel channel;
    private String referenceNumber;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionChannel getChannel() {
        return channel;
    }

    public void setChannel(TransactionChannel channel) {
        this.channel = channel;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}