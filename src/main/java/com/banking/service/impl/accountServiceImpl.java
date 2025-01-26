package com.banking.service.impl;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.banking.dto.AccountDto;
import com.banking.entities.Account;
import com.banking.mapper.AccountMapper;
import com.banking.repostories.AccountRepository;
import com.banking.service.AccountService;



@Service
public class accountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository account_repository;
	
	
	public accountServiceImpl(AccountRepository account_repository) {
		super();
		this.account_repository = account_repository;
	}


	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account= AccountMapper.mapToAccount(accountDto);
		Account saveAccount = account_repository.save(account);
		return AccountMapper.mapToAccountDto(saveAccount);
	}


	@Override
	public AccountDto getAccountById(Long id) {
		 Account account = account_repository.
				 findById(id).
				 orElseThrow(()-> new RuntimeException("Acount not found"));
		return AccountMapper.mapToAccountDto(account);
	}


	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account = account_repository.findById(id).
		orElseThrow(()-> new RuntimeException("account doesn't exist") );
		double balance = account.getBalance();
		account.setBalance(balance+amount);
		Account save = account_repository.save(account);
		
		return AccountMapper.mapToAccountDto(save);
	}


	@Override
	public AccountDto withdraw(Long id, double amount) {
		Account account = account_repository.findById(id).
		orElseThrow(()-> new RuntimeException("Account Doesn't Exist"));
		double balance = account.getBalance();
		try {
			if(balance>=amount) {
				account.setBalance(balance-amount);
				
			}
		} catch (Exception e) {
			
		}
		Account save = account_repository.save(account);
		
		return AccountMapper.mapToAccountDto(save);
	}


	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> allAccounts = account_repository.findAll();
		 return allAccounts.stream().
		map((allAccount)->AccountMapper.
				mapToAccountDto(allAccount)).collect(Collectors.toList());
		
	}


	@Override
	public void deleteAccount(Long id) {
		account_repository.deleteById(id);
		
		
	}

}
