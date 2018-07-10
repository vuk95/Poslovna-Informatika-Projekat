package com.pi.poslovna.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.config.DBConnection;
import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.users.User;
import com.pi.poslovna.service.BankAccountService;
import com.pi.poslovna.service.IzvodXMLWriterService;
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
	
	@Autowired
	private IzvodXMLWriterService izvodXMLService;
	
	@RequestMapping(value = "/bank_accounts", method = RequestMethod.GET)
	public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
		List<BankAccount> bankAccounts = bankAccountService.findAll();
		
		return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/bank_accounts/{id}", method = RequestMethod.GET)
	public ResponseEntity<BankAccount> getBankAccount(@PathVariable Long id) {
		BankAccount bankAccount = bankAccountService.findOne(id);
		
		return new ResponseEntity<>(bankAccount, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/izvod_racuna/{id}", method = RequestMethod.POST, consumes = "text/plain")
	public ResponseEntity<?> izvodRacunaXML(@PathVariable Long id, @RequestBody String path) {
		BankAccount bankAccount = bankAccountService.findOne(id);
		
		izvodXMLService.createIzvodXML(bankAccount, path);
		
		return new ResponseEntity<>(HttpStatus.OK);
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
		
		
		for(BankAccount racun: racuniPravnihLica) {
			parameters.put("COLUMN_6", bank.getName());
			parameters.put("racun_id", racun.getId());
			parameters.put("ime", racun.getIndividual().getName());
			parameters.put("prezime",racun.getIndividual().getLastname());
			parameters.put("broj_racuna",racun.getAccountNumber());
			parameters.put("raspoloziva_sredstva",racun.getMoney());
			
			System.out.println("Banka:" + bank.getName());
			System.out.println("Racuni: " + racun.getId());
			System.out.println("Imena: " + racun.getIndividual().getName());
			System.out.println("Prezimena: " + racun.getIndividual().getLastname());
			System.out.println("Brojevi_Racuna: " + racun.getAccountNumber());
			System.out.println("Novac: " + racun.getMoney());
		}
		
		
		
		try {
			JasperPrint jp = JasperFillManager.fillReport(
			getClass().getResource("/jasper/StanjeRacuna.jasper").openStream(),
			parameters, DBConnection.getInstance().getConnection());
			//eksport
			//File pdf = File.createTempFile("output.", ".pdf");
			JasperExportManager.exportReportToPdfFile(jp, "C:\\Users\\Milovic\\Documents\\proba.pdf");
			//promenite putanju za probu.
		}catch (Exception ex) {
				ex.printStackTrace();
			}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reportLePDF" , method = RequestMethod.GET)
	public ResponseEntity<?> reportLEToPDF(Principal principal) {
		
		User user = userService.getUserByEmail(principal.getName());
		Bank bank = user.getBank();
		
		List<BankAccount> racuniPravnihLica = new ArrayList<BankAccount>();
		
		for(int i=0;i<bank.getLegalEntityClients().size();i++) {
			for(int j=0;j<bank.getLegalEntityClients().get(i).getMojiRacuni().size();j++) {
				racuniPravnihLica.add(bank.getLegalEntityClients().get(i).getMojiRacuni().get(j));
			
			}
		}
		
		
		Map<String, Object> parameters = new HashMap<String, Object>();
				
		parameters.put("ime", bank.getName());
		System.out.println("Banka:" + bank.getName());
		
		for(BankAccount racun: racuniPravnihLica) {
			
			parameters.put("racun_id", racun.getId());
			parameters.put("naziv", racun.getLegalEntity().getName());
			parameters.put("poreski_identifikacioni_broj",racun.getLegalEntity().getPib());
			parameters.put("broj_racuna",racun.getAccountNumber());
			parameters.put("raspoloziva_sredstva",racun.getMoney());
			
			
			System.out.println("Racuni: " + racun.getId());
			System.out.println("Imena: " + racun.getLegalEntity().getName());
			System.out.println("PIB: " + racun.getLegalEntity().getPib());
			System.out.println("Brojevi_Racuna: " + racun.getAccountNumber());
			System.out.println("Novac: " + racun.getMoney());
		}
		
		try {
			JasperPrint jp = JasperFillManager.fillReport(
			getClass().getResource("/jasper/RacuniPravnihLica.jasper").openStream(),
			parameters, DBConnection.getInstance().getConnection());
			//eksport
			//File pdf = File.createTempFile("output.", ".pdf");
			JasperExportManager.exportReportToPdfFile(jp, "C:\\Users\\Milovic\\Documents\\proba1.pdf");
			//promenite putanju za probu.
		}catch (Exception ex) {
				ex.printStackTrace();
			}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}