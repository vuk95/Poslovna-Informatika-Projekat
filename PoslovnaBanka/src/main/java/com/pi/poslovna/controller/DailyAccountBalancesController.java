package com.pi.poslovna.controller;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.converters.AnalyticsOfStatementToAnalyticsOfStatementDTO;
import com.pi.poslovna.converters.DailyAccountToDailyAccountDTO;
import com.pi.poslovna.model.AnalyticsOfStatement;
import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.DailyAccountBalance;
import com.pi.poslovna.model.dto.DailyAccountBalanceDTO;
import com.pi.poslovna.model.dto.AnalyticsOfStatementDTO;
import com.pi.poslovna.service.BankAccountService;
import com.pi.poslovna.service.DailyAccountBalanceService;

@RestController
@RequestMapping(value = "/balansi")
public class DailyAccountBalancesController {
	
	@Autowired
	private BankAccountService racunService;
	
	@Autowired
	private DailyAccountBalanceService balanceService;
	
	@Autowired
	private DailyAccountToDailyAccountDTO toDailyDTO;
	
	@Autowired
	private AnalyticsOfStatementToAnalyticsOfStatementDTO toAnalyticsDTO;
	
	@RequestMapping(value="getBankAccount/{id}", method = RequestMethod.GET)
	public ResponseEntity<BankAccount> getBankAccount(@PathVariable Long id) {
		
		BankAccount bankAccount = racunService.findOne(id);
		
		return new ResponseEntity<>(bankAccount, HttpStatus.OK);		
	}
	
	@RequestMapping(value="getBalances/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<DailyAccountBalanceDTO>> getBalances(@PathVariable Long id) throws IOException {
		
		BankAccount bankAccount = racunService.findOne(id);
	
		List<DailyAccountBalance> lista = bankAccount.getMojiDnevniBalansi();
		List<DailyAccountBalanceDTO> dtoLista = toDailyDTO.convert(lista);
		
		return new ResponseEntity<>(dtoLista, HttpStatus.OK);		
	}
	
	@RequestMapping(value="getBalans/{id}", method = RequestMethod.GET)
	public ResponseEntity<DailyAccountBalance> getBalans(@PathVariable Long id) {
		
		DailyAccountBalance balance = balanceService.findOne(id);
		
		return new ResponseEntity<>(balance, HttpStatus.OK);		
	}
	
	@RequestMapping(value="getAnalytics/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<AnalyticsOfStatementDTO>> getAnalytics(@PathVariable Long id) throws IOException {
		
		DailyAccountBalance balance = balanceService.findOne(id);
	
		List<AnalyticsOfStatement> lista = balance.getMojeAnalitike();
		List<AnalyticsOfStatementDTO> dtoLista = toAnalyticsDTO.convert(lista);
		
		return new ResponseEntity<>(dtoLista, HttpStatus.OK);		
	}

}
