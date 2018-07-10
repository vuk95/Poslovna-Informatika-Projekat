package com.pi.poslovna.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.DailyAccountBalance;

@Repository
public interface DailyAccountBalanceRepository extends JpaRepository<DailyAccountBalance, Long>{

	DailyAccountBalance findByTrafficDateAndRacun(Date trafficDate, BankAccount racun);
	//DailyAccountBalance findByRacun(BankAccount racun);
	ArrayList<DailyAccountBalance> findByRacun(BankAccount racun);
}
