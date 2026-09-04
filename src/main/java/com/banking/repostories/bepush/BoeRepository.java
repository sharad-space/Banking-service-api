package com.banking.repostories.bepush;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.bepush.Boe;

public interface BoeRepository extends JpaRepository<Boe, Long> {

    boolean existsByBoeNumber(String boeNumber);

    Optional<Boe> findByBoeNumber(String boeNumber);
}
