package com.pi.poslovna.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.DailyAccountBalance;
import com.pi.poslovna.repository.DailyAccountBalanceRepository;
import com.pi.poslovna.service.DailyAccountBalanceService;

@Transactional
@Service
public class DailyAccountBalanceServiceImpl implements DailyAccountBalanceService{
	
	@Autowired
	private DailyAccountBalanceRepository dabRepository;
	
	@Override
	public void save(DailyAccountBalance dnevnoStanjeIzvoda) {
		
		dabRepository.save(dnevnoStanjeIzvoda);
	}

}
