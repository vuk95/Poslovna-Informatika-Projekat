package com.pi.poslovna.service.impl;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.DeactivateBankAccount;
import com.pi.poslovna.repository.DeactivateBankAccountRepository;
import com.pi.poslovna.service.BankAccountService;
import com.pi.poslovna.service.DeactivateBankAccountService;

@Transactional
@Service
public class DeactivateBankAccountServiceImpl implements DeactivateBankAccountService {

	@Autowired
	private DeactivateBankAccountRepository deactivateRepository;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Override
	public DeactivateBankAccount create(String accountNumber, BankAccount bankAccount) {
		DeactivateBankAccount deactivate = new DeactivateBankAccount();
		BankAccount sledbenik = bankAccountService.findByAccountNumber(accountNumber);
		if(sledbenik == null) {
			return null;
		}
		
		bankAccountService.prebaciSredstvaSaUgasenog(sledbenik, bankAccount);
		Calendar calendar = Calendar.getInstance();
		Date today = new Date(calendar.getTime().getTime());
		deactivate.setDate(today);
		deactivate.setAccountNumber(accountNumber);
		deactivate.setBankAccount(bankAccount);
		
		return deactivateRepository.save(deactivate);
	}

}
