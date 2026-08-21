package com.banking.repostories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmail(String email);

    Optional<Customer> findByCustomerNumber(String customerNumber);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByMobileNumber(String mobileNumber);

    Optional<Customer> findByPanNumber(String panNumber);
}