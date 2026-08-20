package com.banking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.dto.CustomerDto;
import com.banking.entities.Customer;
import com.banking.mapper.CustomerMapper;
import com.banking.repostories.CustomerRepository;
import com.banking.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        // If email already exists, throw typed exception
        if (customerDto.getEmail() != null && customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new com.banking.exceptions.DuplicateResourceException("Email already in use: " + customerDto.getEmail());
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Customer saved = customerRepository.save(customer);
        return CustomerMapper.mapToCustomerDto(saved);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new com.banking.exceptions.ResourceNotFoundException("Customer not found with id: " + id));
        return CustomerMapper.mapToCustomerDto(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::mapToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}