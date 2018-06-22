package com.pi.poslovna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.repository.BankAccountRepository;
import com.pi.poslovna.service.BankAccountService;

@Transactional
@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	private BankAccountRepository repository;
	
	@Override
	public BankAccount save(BankAccount account) {
		return repository.save(account);
	}

	@Override
	public BankAccount findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<BankAccount> findAll() {
		return repository.findAll();
	}
	
	

}
