package com.banking.repostories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.CustomerBankingSnapshot;

public interface CustomerBankingSnapshotRepository extends JpaRepository<CustomerBankingSnapshot, Long> {

    Optional<CustomerBankingSnapshot> findByCustomerId(Long customerId);
}
