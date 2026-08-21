package com.banking.mapper;

import com.banking.dto.TransactionResponse;
import com.banking.entities.Transaction;

public class TransactionMapper {

    public static TransactionResponse toResponse(Transaction t) {
        TransactionResponse r = new TransactionResponse();
        r.setTransactionId(t.getTransactionId());
        r.setTransactionReference(t.getTransactionReference());
        r.setTransactionType(t.getTransactionType());
        r.setAmount(t.getAmount());
        r.setBalanceBefore(t.getBalanceBefore());
        r.setBalanceAfter(t.getBalanceAfter());
        r.setTransactionDate(t.getTransactionDate());
        r.setDescription(t.getDescription());
        r.setReferenceNumber(t.getReferenceNumber());
        r.setStatus(t.getStatus());
        r.setChannel(t.getChannel());
        if (t.getAccount() != null) {
            r.setAccountId(t.getAccount().getAccountId());
        }
        return r;
    }
}