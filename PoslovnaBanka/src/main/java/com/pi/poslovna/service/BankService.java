package com.pi.poslovna.service;

import java.util.List;

import com.pi.poslovna.model.Bank;

public interface BankService {

	public Bank findOne(Long id);
	public List<Bank> findAll();
	public Bank save(Bank bank);
	public Bank delete(Long id);
}
