package com.pi.poslovna.controller;

import java.security.Principal;
import java.sql.DriverManager;
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

import com.mysql.jdbc.Connection;
import com.pi.poslovna.config.DBConnection;
import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.users.User;
import com.pi.poslovna.service.BankAccountService;
import com.pi.poslovna.service.IzvodXMLWriterService;
import com.pi.poslovna.service.UserService;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

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
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("ime_banke", bank.getName());
		
		
		try {
			
			Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useSSL=true&createDatabaseIfNotExist=true","root","isatim32");
			JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResource("/jasper/StanjeRacuna.jrxml").openStream());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
			
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\Milovic\\Documents\\proba5.pdf");
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
		
		
		Map<String, Object> parameters = new HashMap<String, Object>();
				
		parameters.put("banka_ime", bank.getName());
		
		
		try {
			Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useSSL=true&createDatabaseIfNotExist=true","root","isatim32");
			JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResource("/jasper/StanjeRacuna2.jrxml").openStream());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\Milovic\\Documents\\proba10.pdf");
			//promenite putanju za probu.
		}catch (Exception ex) {
				ex.printStackTrace();
			}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reportAnalitycs" , method = RequestMethod.GET)
	public ResponseEntity<?> reportAnalyticsToPDF() {
		
		try {
			Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useSSL=true&createDatabaseIfNotExist=true","root","isatim32");
			JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResource("/jasper/IzvodKlijenata.jrxml").openStream());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, con);
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\Milovic\\Documents\\proba8.pdf");
			//promenite putanju za probu.
		}catch (Exception ex) {
				ex.printStackTrace();
			}
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	
}