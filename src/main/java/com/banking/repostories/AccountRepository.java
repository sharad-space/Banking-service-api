package com.banking.repostories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
