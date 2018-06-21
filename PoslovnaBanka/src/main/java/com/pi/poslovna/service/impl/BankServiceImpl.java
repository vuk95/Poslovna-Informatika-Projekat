package com.pi.poslovna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.Bank;
import com.pi.poslovna.repository.BankRepository;
import com.pi.poslovna.service.BankService;

@Transactional
@Service
public class BankServiceImpl implements BankService {

	@Autowired
	public BankRepository bankRepository;
	
	@Override
	public Bank findOne(Long id) {
		return bankRepository.findOne(id);
	}

	@Override
	public List<Bank> findAll() {
		return bankRepository.findAll();
	}

	@Override
	public Bank save(Bank bank) {
		return bankRepository.save(bank);
	}

	@Override
	public Bank delete(Long id) {
		Bank bank = bankRepository.findOne(id);
		
		if(bank == null) {
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant bank");
		}
		
		bankRepository.delete(bank);
		
		return bank;
	}

}
