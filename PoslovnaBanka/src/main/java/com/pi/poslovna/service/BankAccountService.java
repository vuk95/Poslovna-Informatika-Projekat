package com.pi.poslovna.service;

import java.util.List;

import com.pi.poslovna.model.BankAccount;

public interface BankAccountService {
	
	public BankAccount findOne(Long id);
	public BankAccount findByAccountNumber(String accountNumber);
	public List<BankAccount> findAll();
	public BankAccount save(BankAccount account);
	public void prebaciSredstvaSaUgasenog(BankAccount sledbenik, BankAccount ugasen);
	
}
