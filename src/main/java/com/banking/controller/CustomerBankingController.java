package com.banking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.CustomerBankingDetailDto;
import com.banking.service.CustomerBankingService;

@RestController
@RequestMapping("/api")
public class CustomerBankingController {

    private final CustomerBankingService customerBankingService;

    public CustomerBankingController(CustomerBankingService customerBankingService) {
        this.customerBankingService = customerBankingService;
    }

    @GetMapping("/customers/{customerId}/banking-details")
    public ResponseEntity<CustomerBankingDetailDto> getCustomerBankingDetails(@PathVariable Long customerId) {
        CustomerBankingDetailDto response = customerBankingService.getCustomerBankingDetails(customerId);
        return ResponseEntity.ok(response);
    }
}
