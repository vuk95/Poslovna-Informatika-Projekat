package com.pi.poslovna.service;

import java.util.Date;

import com.pi.poslovna.model.DailyAccountBalance;

public interface DailyAccountBalanceService {
	
	public void save(DailyAccountBalance dnevnoStanjeIzvoda);
	public DailyAccountBalance findByTrafficDate(Date trafficDate);
}
