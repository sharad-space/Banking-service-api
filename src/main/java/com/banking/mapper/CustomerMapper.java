package com.banking.mapper;

import com.banking.dto.CustomerDto;
import com.banking.entities.Customer;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerDto dto) {
        // For new customers id should be null so JPA treats it as a new entity
        Long id = (dto.getId() == null || dto.getId() <= 0) ? null : dto.getId();
        return new Customer(
                id,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhone()
        );
    }

    public static CustomerDto mapToCustomerDto(Customer entity) {
        return new CustomerDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone()
        );
    }
}