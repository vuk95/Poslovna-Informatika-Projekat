package com.pi.poslovna.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.poslovna.model.DailyAccountBalance;

@Repository
public interface DailyAccountBalanceRepository extends JpaRepository<DailyAccountBalance, Long>{

	DailyAccountBalance findByTrafficDate(Date trafficDate);
	
}