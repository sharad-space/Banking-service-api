package com.banking.repostories.read;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.read.CustomerBankingSnapshot;

public interface CustomerBankingSnapshotRepository extends JpaRepository<CustomerBankingSnapshot, Long> {

    Optional<CustomerBankingSnapshot> findByCustomerId(Long customerId);
}
