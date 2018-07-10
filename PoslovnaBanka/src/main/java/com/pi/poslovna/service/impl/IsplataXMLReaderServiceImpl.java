package com.pi.poslovna.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pi.poslovna.model.AnalyticsOfStatement;
import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.DailyAccountBalance;
import com.pi.poslovna.service.AnalyticsOfStatementService;
import com.pi.poslovna.service.BankAccountService;
import com.pi.poslovna.service.DailyAccountBalanceService;
import com.pi.poslovna.service.IsplataXMLReaderService;


@Transactional
@Service
public class IsplataXMLReaderServiceImpl  implements IsplataXMLReaderService{

	@Autowired
	private AnalyticsOfStatementService analyticServise;
	
	@Autowired
	private DailyAccountBalanceService balanceService;
	
	@Autowired
	private BankAccountService accountService;
	
	@Override
	public void readIsplataXML(String filePath) {
		
		try {
			
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);
		
		doc.getDocumentElement().normalize();
		
		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		
		AnalyticsOfStatement analitika = new AnalyticsOfStatement();
		
		NodeList platioc = doc.getElementsByTagName("podaci_o_platiocu");
		
		Node plat = platioc.item(0);
		
		//Platioc (Uplatioc)
		if(plat.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element) plat;
			analitika.setDebtor(elem.getElementsByTagName("naziv_platioca").item(0).getTextContent());	
		}
		
		//Svrha isplate
		Node svrha = doc.getElementsByTagName("svrha_isplate").item(0);
		analitika.setPurposeOfPayment(svrha.getTextContent());
		
		//Primaoc
		NodeList primaoc = doc.getElementsByTagName("podaci_o_primaocu");
		Node prim = primaoc.item(0);
		
		if(prim.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element) prim;
			analitika.setRecipient(elem.getElementsByTagName("naziv_primaoca").item(0).getTextContent());
		}
		
		//Podaci o isplati
		NodeList isplata = doc.getElementsByTagName("podaci_o_isplati");
		Node ispl = isplata.item(0);
		
		if(ispl.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element) ispl;
			
			//Iznos
			float sum = Float.parseFloat(elem.getElementsByTagName("iznos").item(0).getTextContent());
			analitika.setSum(sum);
			
			//Racun platioca
			analitika.setDebtorAccount(elem.getElementsByTagName("racun_platioca").item(0).getTextContent());
			
			//Model zaduzenja
			int modelAssigments = Integer.parseInt(elem.getElementsByTagName("model").item(0).getTextContent());
			analitika.setModelAssigments(modelAssigments);
			
			//Poziv na broj zaduzenja
			analitika.setReferenceNumberAssigments(elem.getElementsByTagName("poziv_na_broj").item(0).getTextContent());
			
		}
		
		//Podaci o prijemu
		NodeList prijem = doc.getElementsByTagName("podaci_o_prijemu");
		Node prij = prijem.item(0);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		if(prij.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element)  prij;
			Date dateOfReceipt = format.parse(elem.getElementsByTagName("datum_prijema").item(0).getTextContent());
			analitika.setDateOfReceipt(dateOfReceipt);
		}
		else {
			System.out.println("Nema datuma...");
		}
		
		//Datum valute (Datum izvrsenja)
		Node datumValute = doc.getElementsByTagName("datum_valute").item(0);
		
		if(doc.getElementsByTagName("datum_valute").item(0).getTextContent() != "") {
			Date currencyDate = format.parse(datumValute.getTextContent());
			analitika.setCurrencyDate(currencyDate);
		}
		else {
			System.out.println("Nema datuma...");
		}
		
		BankAccount founded = accountService.findByAccountNumber(analitika.getDebtorAccount());
		DailyAccountBalance found = balanceService.findByTrafficDateAndRacun(analitika.getDateOfReceipt(), founded);
		
		if(found == null) {
			BankAccount racun = accountService.findByAccountNumber(analitika.getDebtorAccount());
			DailyAccountBalance nadjeniPoRacunu = balanceService.findByRacun(racun);
			
			//kreiramo novo dnevno stanje racuna za svaku analitiku
			DailyAccountBalance dab = new DailyAccountBalance();
			//Nisam siguran da li je ovo traffic date
			dab.setTrafficDate(analitika.getDateOfReceipt());
			//prihod je suma uplate iz analitike
			dab.setTrafficToBenefit(0.0f);
			//gubitak je u slucaju naloga za uplatu 0
			dab.setTrafficToTheBurden(analitika.getSum());
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
					//System.out.println(datumFound + "before " + datumAnalitika);
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
			analitika.setDnevnoStanjeIzvoda(dab);
			dab.getMojeAnalitike().add(analitika);
			
			balanceService.save(dab);
			analyticServise.save(analitika);
			}
			else {
			
			if(found.getRacun() == founded) {
				
				//System.out.println("Found racun:"  + found.getRacun().getAccountNumber());
				//System.out.println("Iz analitike:" + founded.getAccountNumber());
		
				found.setTrafficDate(analitika.getDateOfReceipt());
				//prihod je suma uplate iz analitike
				found.setTrafficToBenefit(0.0f);
				//gubitak je u slucaju naloga za uplatu 0
				found.setTrafficToTheBurden(analitika.getSum());
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
				if(datumFound.compareTo(datumAnalitika) == 0) {
					
					//System.out.println(datumFound + "equals " + datumAnalitika);
					found.setNewState(found.getNewState() + found.getPreviousState() + found.getTrafficToBenefit() - found.getTrafficToTheBurden());
					if(upamtiPrethodnoStanje != 0.0f)
					found.setPreviousState(upamtiPrethodnoStanje);
					
					
				}
				/*
				else {
					found.setPreviousState(0.0f);
					found.setNewState(found.getPreviousState() + found.getTrafficToBenefit() - found.getTrafficToTheBurden());
					
				}*/
				//nadjemo taj racun iz analitike
				BankAccount racun = accountService.findByAccountNumber(analitika.getDebtorAccount());
				//staro stanje je stanje sa racuna a novo suma starog prihoda i gubitka
				//found.setPreviousState(Float.parseFloat(racun.getMoney()));

				//setujemo novo stanje i na racun
				racun.setMoney(found.getNewState().toString());
				
				found.setRacun(racun);
				analitika.setDnevnoStanjeIzvoda(found);
				found.getMojeAnalitike().add(analitika);
				
				balanceService.save(found);
				//analyticsService.save(analitika);
				
			}

		}
	
		
		/*
			DailyAccountBalance dab = new DailyAccountBalance();
			//Nisam siguran da li je ovo traffic date
			dab.setTrafficDate(analitika.getDateOfReceipt());
		//	dab.setTrafficToTheBurden(analitika.getSum());
		//	dab.setTrafficToBenefit(0.0f);
			BankAccount racunUplatioca = accountService.findByAccountNumber(analitika.getDebtorAccount());
			dab.setPreviousState(Float.parseFloat(racunUplatioca.getMoney()));
			dab.setNewState(dab.getPreviousState() + dab.getTrafficToBenefit() - dab.getTrafficToTheBurden());
			racunUplatioca.setMoney(dab.getNewState().toString());
			dab.setRacun(racunUplatioca);
			balanceService.save(dab);
			analitika.setDnevnoStanjeIzvoda(dab);
			analyticServise.save(analitika);
		*/
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}