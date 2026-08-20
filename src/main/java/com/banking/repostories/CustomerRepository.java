package com.banking.repostories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmail(String email);

}