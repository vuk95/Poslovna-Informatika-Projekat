package com.pi.poslovna.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.BankAccount;
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

	@Override
	public DailyAccountBalance findByTrafficDateAndRacun(Date trafficDate, BankAccount racun) {
		return dabRepository.findByTrafficDateAndRacun(trafficDate, racun);
	}

	@Override
	public DailyAccountBalance findByRacun(BankAccount racun) {
		// TODO Auto-generated method stub
		ArrayList<DailyAccountBalance> lista = dabRepository.findByRacun(racun);
		ArrayList<Date> listaDatuma = new ArrayList<Date>();
		DailyAccountBalance balans = null;
		
		if(!lista.isEmpty()) {
			//System.out.println(lista.size());
			
			for(int i = 0; i < lista.size(); i++) {
			listaDatuma.add(lista.get(i).getTrafficDate());
		}
		
		Date newest = Collections.max(listaDatuma);
		
		for(int i = 0; i < lista.size(); i++) {
			if(lista.get(i).getTrafficDate() == newest) {
				balans = lista.get(i);
			}
		}
		}
		//if(balans != null)
		//System.out.println(balans.getTrafficDate());
		return balans;
	}

	/*@Override
	public DailyAccountBalance findByRacun(BankAccount racun) {
		return dabRepository.findByRacun(racun);
	}
	 */
}
