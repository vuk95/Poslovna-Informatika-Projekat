package com.pi.poslovna.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import com.pi.poslovna.model.AnalyticsOfStatement;
import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.DailyAccountBalance;
import com.pi.poslovna.service.AnalyticsOfStatementService;
import com.pi.poslovna.service.BankAccountService;
import com.pi.poslovna.service.DailyAccountBalanceService;
import com.pi.poslovna.service.UplataXMLReaderService;

@Transactional
@Service
public class UplataXMLReaderServiceImpl implements UplataXMLReaderService {
	
	@Autowired
	private AnalyticsOfStatementService analyticsService;

	@Autowired
	private DailyAccountBalanceService balanceService;
	
	@Autowired
	private BankAccountService accountService;
	
	
	@Override
	public void readUplataXML(String filePath) {
		try {
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			
			AnalyticsOfStatement analitika = new AnalyticsOfStatement();
			
			NodeList uplatioc = doc.getElementsByTagName("podaci_o_uplatiocu");
			
			Node upl = uplatioc.item(0);
						
			if(upl.getNodeType() == Node.ELEMENT_NODE) {
				//uplatioc
				Element el1 = (Element) upl;
				analitika.setDebtor(el1.getElementsByTagName("naziv_uplatioca").item(0).getTextContent());		
			}
			
			//svrha uplate
			
			Node svrha = doc.getElementsByTagName("svrha_uplate").item(0);
			analitika.setPurposeOfPayment(svrha.getTextContent());
			
			NodeList primaoc = doc.getElementsByTagName("podaci_o_primaocu");
			
			Node prim = primaoc.item(0);
			
			if(prim.getNodeType() == Node.ELEMENT_NODE) {
				//primaoc
				Element el1 = (Element) prim;
				analitika.setRecipient(el1.getElementsByTagName("naziv_primaoca").item(0).getTextContent());		
			}
			
			NodeList podaci_upl = doc.getElementsByTagName("podaci_o_uplati");
			
			Node uplata = podaci_upl.item(0);
			
			if(uplata.getNodeType() == Node.ELEMENT_NODE) {
				//racun onog ko prima,iznos, model i poziv na broj
				Element el1 = (Element) uplata;
				analitika.setAccountRecipient(el1.getElementsByTagName("racun_primaoca").item(0).getTextContent());
	
				//Iznos
				float sum = Float.parseFloat(el1.getElementsByTagName("iznos").item(0).getTextContent());
				analitika.setSum(sum);
				
				int modelApproval =Integer.parseInt(el1.getElementsByTagName("model").item(0).getTextContent());
				analitika.setModelApproval(modelApproval);
				
				analitika.setReferenceNumberApproval(el1.getElementsByTagName("poziv_na_broj").item(0).getTextContent());
				
			}
			
			NodeList podaci_pri = doc.getElementsByTagName("podaci_o_prijemu");
			
			Node pri = podaci_pri.item(0);
			
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			
			if(pri.getNodeType() == Node.ELEMENT_NODE) {
				//datum prijema
				Element el1 = (Element) pri;
				if(el1.getElementsByTagName("datum_prijema").item(0).getTextContent() != "") {
					Date dateOfReceipt = format.parse(el1.getElementsByTagName("datum_prijema").item(0).getTextContent());
					analitika.setDateOfReceipt(dateOfReceipt);
				}
				else {
					System.out.println("Nema datuma...");
				}
			
			}
			//datum valute
			Node datval = doc.getElementsByTagName("datum_izvrsenja").item(0);
			if(doc.getElementsByTagName("datum_izvrsenja").item(0).getTextContent() != "") {
				Date currencyDate = format.parse(datval.getTextContent());
				analitika.setCurrencyDate(currencyDate);
			}
			else {
				System.out.println("Nema datuma...");
			}
			
			BankAccount founded = accountService.findByAccountNumber(analitika.getAccountRecipient());
			DailyAccountBalance found = balanceService.findByTrafficDateAndRacun(analitika.getDateOfReceipt(), founded);
			
			
			if(found == null) {
				BankAccount racun = accountService.findByAccountNumber(analitika.getAccountRecipient());
				DailyAccountBalance nadjeniPoRacunu = balanceService.findByRacun(racun);
				
			
			//kreiramo novo dnevno stanje racuna za svaku analitiku
			DailyAccountBalance dab = new DailyAccountBalance();
			//Nisam siguran da li je ovo traffic date
			dab.setTrafficDate(analitika.getDateOfReceipt());
			//prihod je suma uplate iz analitike
			dab.setTrafficToBenefit(analitika.getSum());
			//gubitak je u slucaju naloga za uplatu 0
			dab.setTrafficToTheBurden(0.0f);
			//nadjemo taj racun iz analitike
			//staro stanje je stanje sa racuna a novo suma starog prihoda i gubitka
			//dab.setPreviousState(Float.parseFloat(racun.getMoney()));
			if(nadjeniPoRacunu != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String datFound = sdf.format(nadjeniPoRacunu.getTrafficDate());
				String datAnalitika = sdf.format(analitika.getDateOfReceipt());
				Date datumFound = sdf.parse(datFound);
				Date datumAnalitika = sdf.parse(datAnalitika);
				if(datumFound.compareTo(datumAnalitika) < 0) {
					System.out.println(datumFound + "before " + datumAnalitika);
					dab.setPreviousState(nadjeniPoRacunu.getNewState());
				}
			}
			else {
			dab.setPreviousState(0.0f);
			}
			dab.setNewState(dab.getPreviousState() + dab.getTrafficToBenefit() - dab.getTrafficToTheBurden());
			//setujemo novo stanje i na racun
			racun.setMoney(dab.getNewState().toString());
			
			dab.setRacun(racun);
			balanceService.save(dab);
			analitika.setDnevnoStanjeIzvoda(dab);
			analyticsService.save(analitika);
			}
			else {
				
				
				/*if(found.getTrafficDate().equals(analitika.getDateOfReceipt())){
					System.out.println(found.getTrafficDate().toString() + "je isto kad i:");
					System.out.println(analitika.getDateOfReceipt().toString());
				}
				else if(found.getTrafficDate().before(analitika.getDateOfReceipt())) {
					System.out.println(found.getTrafficDate().toString() + "je pre:");
					System.out.println(analitika.getDateOfReceipt().toString());
				}
				else if(found.getTrafficDate().after(analitika.getDateOfReceipt())) {
					System.out.println(found.getTrafficDate().toString() + "je posle:");
					System.out.println(analitika.getDateOfReceipt().toString());
				}
				else {
					System.out.println("How to get here?");
				}*/
				
				
				if(found.getRacun() == founded) {
					
					System.out.println("Found racun:"  + found.getRacun().getAccountNumber());
					System.out.println("Iz analitike:" + founded.getAccountNumber());
					//System.out.println(found.getTrafficDate().toString());
					
					
					
					
					//if(found.getTrafficDate().equals(analitika.getDateOfReceipt())) {
						//System.out.println("Usao ovde");
					
					
					found.setTrafficDate(analitika.getDateOfReceipt());
					//prihod je suma uplate iz analitike
					found.setTrafficToBenefit(analitika.getSum());
					//gubitak je u slucaju naloga za uplatu 0
					found.setTrafficToTheBurden(0.0f);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String datFound = sdf.format(found.getTrafficDate());
					String datAnalitika = sdf.format(analitika.getDateOfReceipt());
					Date datumFound = sdf.parse(datFound);
					Date datumAnalitika = sdf.parse(datAnalitika);
				
					Float upamtiPrethodnoStanje = 0.0f;
					
					if(found.getPreviousState() == 0.0f) {
						found.setPreviousState(0.0f);
					}
					else{
						
						upamtiPrethodnoStanje = found.getPreviousState();
						found.setPreviousState(0.0f);
					}
					//found.setPreviousState(0.0f);
					System.out.println(upamtiPrethodnoStanje);
					if(datumFound.compareTo(datumAnalitika) == 0) {
						
						System.out.println(datumFound + "equals " + datumAnalitika);
						found.setNewState(found.getNewState() + found.getPreviousState() + found.getTrafficToBenefit() - found.getTrafficToTheBurden());
						if(upamtiPrethodnoStanje != 0.0f)
						found.setPreviousState(upamtiPrethodnoStanje);
						
						
					}
					else {
						found.setPreviousState(0.0f);
						found.setNewState(found.getPreviousState() + found.getTrafficToBenefit() - found.getTrafficToTheBurden());
						
					}
					//nadjemo taj racun iz analitike
					BankAccount racun = accountService.findByAccountNumber(analitika.getAccountRecipient());
					//staro stanje je stanje sa racuna a novo suma starog prihoda i gubitka
				
					//found.setPreviousState(Float.parseFloat(racun.getMoney()));
					
					//setujemo novo stanje i na racun
					racun.setMoney(found.getNewState().toString());
					
					found.setRacun(racun);
					balanceService.save(found);
					analitika.setDnevnoStanjeIzvoda(found);
					analyticsService.save(analitika);
					
				}
				else {
					
					if(found.getTrafficDate().compareTo(analitika.getDateOfReceipt()) < 0) {
						System.out.println(found.getTrafficDate().toString() + "je pre:");
						System.out.println(analitika.getDateOfReceipt().toString());
					}
					if(found.getTrafficDate().compareTo(analitika.getDateOfReceipt()) > 0) {
						System.out.println(found.getTrafficDate().toString() + "je posle:");
						System.out.println(analitika.getDateOfReceipt().toString());
					}
					
					//kreiramo novo dnevno stanje racuna za svaku analitiku
					DailyAccountBalance dab = new DailyAccountBalance();
					//Nisam siguran da li je ovo traffic date
					dab.setTrafficDate(analitika.getDateOfReceipt());
					//prihod je suma uplate iz analitike
					dab.setTrafficToBenefit(analitika.getSum());
					//gubitak je u slucaju naloga za uplatu 0
					dab.setTrafficToTheBurden(0.0f);
					//nadjemo taj racun iz analitike
					BankAccount racun = accountService.findByAccountNumber(analitika.getAccountRecipient());
					//staro stanje je stanje sa racuna a novo suma starog prihoda i gubitka
					dab.setPreviousState(0.0f);
					dab.setNewState(dab.getPreviousState() + dab.getTrafficToBenefit() - dab.getTrafficToTheBurden());
					//setujemo novo stanje i na racun
					racun.setMoney(dab.getNewState().toString());
					
					dab.setRacun(racun);
					balanceService.save(dab);
					analitika.setDnevnoStanjeIzvoda(dab);
					analyticsService.save(analitika);
				}
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
