package com.pi.poslovna.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.config.DBConnection;
import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.users.User;
import com.pi.poslovna.service.BankAccountService;
import com.pi.poslovna.service.UserService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
public class BankAccountController {

	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/bank_accounts", method = RequestMethod.GET)
	public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
		List<BankAccount> bankAccounts = bankAccountService.findAll();
		
		return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/reportPDF" , method = RequestMethod.GET)
	public ResponseEntity<?> reportToPDF(Principal principal) {
		
		User user = userService.getUserByEmail(principal.getName());
		Bank bank = user.getBank();
		
		
		List<BankAccount> racuniPravnihLica = new ArrayList<BankAccount>();
		
		for(int i=0;i<bank.getIndividualClients().size();i++) {
			for(int j=0;j<bank.getIndividualClients().get(i).getMojiRacuni().size();j++) {
				racuniPravnihLica.add(bank.getIndividualClients().get(i).getMojiRacuni().get(j));
			
			}
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("COLUMN_6", bank.getName());
		for(int i=0;i<racuniPravnihLica.size();i++) {
			parameters.put("racun_id", racuniPravnihLica.get(i).getId());
			parameters.put("ime", racuniPravnihLica.get(i).getIndividual().getName());
			parameters.put("prezime",racuniPravnihLica.get(i).getIndividual().getLastname());
			parameters.put("broj_racuna",racuniPravnihLica.get(i).getAccountNumber());
			parameters.put("raspoloziva_sredstva",racuniPravnihLica.get(i).getMoney());
		}
		
		
		try {
			JasperPrint jp = JasperFillManager.fillReport(
			getClass().getResource("/jasper/StanjeRacuna.jasper").openStream(),
			parameters, DBConnection.getInstance().getConnection());
			//eksport
			File pdf = File.createTempFile("output.", ".pdf");
			JasperExportManager.exportReportToPdfStream(jp, new FileOutputStream(pdf));
		}catch (Exception ex) {
				ex.printStackTrace();
			}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
