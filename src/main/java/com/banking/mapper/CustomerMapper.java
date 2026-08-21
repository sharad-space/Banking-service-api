package com.banking.mapper;

import com.banking.dto.CustomerDto;
import com.banking.entities.Customer;
import com.banking.entities.CustomerStatus;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerDto dto) {
        Customer entity = new Customer();
        entity.setCustomerId(dto.getId());
        entity.setCustomerNumber(dto.getCustomerNumber());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setMobileNumber(dto.getPhone());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setPanNumber(dto.getPanNumber());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setPostalCode(dto.getPostalCode());
        if (dto.getStatus() != null && !dto.getStatus().trim().isEmpty()) {
            entity.setStatus(CustomerStatus.valueOf(dto.getStatus()));
        }
        return entity;
    }

    public static CustomerDto mapToCustomerDto(Customer entity) {
        CustomerDto dto = new CustomerDto();
        dto.setId(entity.getCustomerId());
        dto.setCustomerNumber(entity.getCustomerNumber());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getMobileNumber());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setPanNumber(entity.getPanNumber());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setPostalCode(entity.getPostalCode());
        if (entity.getStatus() != null) {
            dto.setStatus(entity.getStatus().name());
        }
        return dto;
    }
}