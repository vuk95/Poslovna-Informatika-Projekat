package com.pi.poslovna.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.DailyAccountBalance;

public interface DailyAccountBalanceService {
	
	public void save(DailyAccountBalance dnevnoStanjeIzvoda);
	DailyAccountBalance findByTrafficDateAndRacun(Date trafficDate, BankAccount racun);
}
