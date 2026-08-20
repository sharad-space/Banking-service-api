package com.banking.service;

import java.util.List;

import com.banking.dto.CustomerDto;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto getCustomerById(Long id);

    List<CustomerDto> getAllCustomers();

    void deleteCustomer(Long id);
}