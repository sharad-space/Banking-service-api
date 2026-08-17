package com.banking.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.AccountDto;
import com.banking.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	
	private AccountService accountService;

	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}
	
	// add account res api
	
	@PostMapping
	public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
		return new ResponseEntity<AccountDto>(accountService.createAccount(accountDto),HttpStatus.CREATED);
		
	}

//	public static void main(String[] args) {
//		System.out.println();
//		System.out.println("AccountController.main");
//		System.out.printf("");
//		System.out.println("args = " + Arrays.toString(args));
//		System.out.println("true = " + true);
//	}
	
	// Get Account Rest Api
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
		AccountDto accountById = accountService.getAccountById(id);
		return ResponseEntity.ok(accountById);
	}
	
	//deposit REST API
	
	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDto> deposit(@PathVariable Long id, 
			@RequestBody Map<String, Double> request) {
		 Double amount = request.get("amount");
		AccountDto deposit = accountService.deposit(id,amount);
		
		return ResponseEntity.ok(deposit);
	}
	
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, 
			@RequestBody Map<String, Double> request) {
		 Double amount = request.get("amount");
		AccountDto withdraw = accountService.withdraw(id,amount);
		
		return ResponseEntity.ok(withdraw);
	}
	
	@GetMapping("/accounts")
	public ResponseEntity<List<AccountDto>> getAllAcounts(){
		List<AccountDto> allAccounts = accountService.getAllAccounts();
		
		return ResponseEntity.ok(allAccounts);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id){
		accountService.deleteAccount(id);
		
		return  ResponseEntity.ok("Account deleted");
	}
	

}
