package com.pi.poslovna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.service.BankAccountService;

@RestController
public class BankAccountController {

	@Autowired
	private BankAccountService bankAccountService;
	
	@RequestMapping(value = "/bank_accounts", method = RequestMethod.GET)
	public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
		List<BankAccount> bankAccounts = bankAccountService.findAll();
		
		return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
	}
	
}
