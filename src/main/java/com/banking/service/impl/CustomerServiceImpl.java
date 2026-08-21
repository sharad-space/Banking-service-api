package com.banking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.dto.CustomerDto;
import com.banking.entities.Customer;
import com.banking.entities.CustomerStatus;
import com.banking.exceptions.BadRequestException;
import com.banking.exceptions.DuplicateResourceException;
import com.banking.exceptions.ResourceNotFoundException;
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
        if (customerDto == null) {
            throw new BadRequestException("Customer details are required");
        }
        if (customerDto.getEmail() != null && customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new DuplicateResourceException("Email already in use: " + customerDto.getEmail());
        }
        // Ensure customerNumber exists and is unique. Generate if missing.
        String customerNumber = customerDto.getCustomerNumber();
        if (customerNumber != null && customerRepository.findByCustomerNumber(customerNumber).isPresent()) {
            throw new DuplicateResourceException("Customer number already exists: " + customerNumber);
        }
        if (customerNumber == null || customerNumber.trim().isEmpty()) {
            // generate simple customer number (timestamp-based) - safe for dev; replace with sequence in prod
            String gen;
            do {
                gen = "CUST-" + System.currentTimeMillis();
            } while (customerRepository.findByCustomerNumber(gen).isPresent());
            customerDto.setCustomerNumber(gen);
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto);
        Customer saved = customerRepository.save(customer);
        return CustomerMapper.mapToCustomerDto(saved);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return CustomerMapper.mapToCustomerDto(customer);
    }

    @Override
    public CustomerDto getCustomerByCustomerNumber(String customerNumber) {
        Customer customer = customerRepository.findByCustomerNumber(customerNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with number: " + customerNumber));
        return CustomerMapper.mapToCustomerDto(customer);
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        if (customerDto.getFirstName() != null) {
            customer.setFirstName(customerDto.getFirstName());
        }
        if (customerDto.getLastName() != null) {
            customer.setLastName(customerDto.getLastName());
        }
        if (customerDto.getEmail() != null) {
            customer.setEmail(customerDto.getEmail());
        }
        if (customerDto.getPhone() != null) {
            customer.setMobileNumber(customerDto.getPhone());
        }
        if (customerDto.getAddress() != null) {
            customer.setAddress(customerDto.getAddress());
        }
        if (customerDto.getCity() != null) {
            customer.setCity(customerDto.getCity());
        }
        if (customerDto.getState() != null) {
            customer.setState(customerDto.getState());
        }
        if (customerDto.getPostalCode() != null) {
            customer.setPostalCode(customerDto.getPostalCode());
        }
        if (customerDto.getStatus() != null && !customerDto.getStatus().trim().isEmpty()) {
            customer.setStatus(CustomerStatus.valueOf(customerDto.getStatus()));
        }
        return CustomerMapper.mapToCustomerDto(customerRepository.save(customer));
    }

    @Override
    public CustomerDto deactivateCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customer.setStatus(CustomerStatus.INACTIVE);
        return CustomerMapper.mapToCustomerDto(customerRepository.save(customer));
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::mapToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}