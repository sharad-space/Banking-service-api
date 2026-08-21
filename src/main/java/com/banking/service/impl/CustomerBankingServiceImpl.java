package com.banking.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.dto.AccountDto;
import com.banking.dto.CustomerBankingAccountDetailDto;
import com.banking.dto.CustomerBankingDetailDto;
import com.banking.dto.TransactionResponse;
import com.banking.entities.Account;
import com.banking.entities.Customer;
import com.banking.entities.CustomerBankingSnapshot;
import com.banking.entities.Transaction;
import com.banking.exceptions.ResourceNotFoundException;
import com.banking.mapper.AccountMapper;
import com.banking.mapper.CustomerMapper;
import com.banking.mapper.TransactionMapper;
import com.banking.repostories.AccountRepository;
import com.banking.repostories.CustomerBankingSnapshotRepository;
import com.banking.repostories.CustomerRepository;
import com.banking.repostories.TransactionRepository;
import com.banking.service.CustomerBankingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomerBankingServiceImpl implements CustomerBankingService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CustomerBankingSnapshotRepository snapshotRepository;
    private final ObjectMapper objectMapper;

    public CustomerBankingServiceImpl(CustomerRepository customerRepository,
                                     AccountRepository accountRepository,
                                     TransactionRepository transactionRepository,
                                     CustomerBankingSnapshotRepository snapshotRepository,
                                     ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.snapshotRepository = snapshotRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public CustomerBankingDetailDto getCustomerBankingDetails(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        List<Account> accounts = accountRepository.findByCustomerCustomerId(customerId);
        List<CustomerBankingAccountDetailDto> accountDetails = new ArrayList<>();

        for (Account account : accounts) {
            AccountDto accountDto = AccountMapper.mapToAccountDto(account);
            List<Transaction> transactions = transactionRepository.findByAccountAccountId(account.getAccountId());
            List<TransactionResponse> transactionResponses = new ArrayList<>();
            for (Transaction transaction : transactions) {
                transactionResponses.add(TransactionMapper.toResponse(transaction));
            }
            accountDetails.add(new CustomerBankingAccountDetailDto(accountDto, transactionResponses));
        }

        CustomerBankingDetailDto response = new CustomerBankingDetailDto(
                customer.getCustomerId(),
                CustomerMapper.mapToCustomerDto(customer),
                accountDetails
        );

        persistSnapshot(customer, response);
        return response;
    }

    private void persistSnapshot(Customer customer, CustomerBankingDetailDto response) {
        try {
            String payload = objectMapper.writeValueAsString(response);
            Optional<CustomerBankingSnapshot> existing = snapshotRepository.findByCustomerId(customer.getCustomerId());
            CustomerBankingSnapshot snapshot = existing.orElse(new CustomerBankingSnapshot());
            snapshot.setCustomerId(customer.getCustomerId());
            snapshot.setCustomerNumber(customer.getCustomerNumber());
            snapshot.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
            snapshot.setEmail(customer.getEmail());
            snapshot.setSnapshotJson(payload);
            snapshot.setLastSyncedAt(LocalDateTime.now());
            snapshotRepository.save(snapshot);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to persist customer banking details snapshot", e);
        }
    }
}
