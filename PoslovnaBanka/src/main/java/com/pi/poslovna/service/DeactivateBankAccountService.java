package com.pi.poslovna.service;

import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.DeactivateBankAccount;

public interface DeactivateBankAccountService {

	public DeactivateBankAccount create(String accountNumber, BankAccount bankAccount);
}
