package com.banking.service;

import java.util.List;

import com.banking.dto.CustomerDto;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto getCustomerById(Long id);

    CustomerDto getCustomerByCustomerNumber(String customerNumber);

    CustomerDto updateCustomer(Long id, CustomerDto customerDto);

    CustomerDto deactivateCustomer(Long id);

    List<CustomerDto> getAllCustomers();

    void deleteCustomer(Long id);
}