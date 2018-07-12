package com.pi.poslovna.service.impl;

import java.io.File;
import java.security.Principal;
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
import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.DailyAccountBalance;
import com.pi.poslovna.model.InterbankTransfer;
import com.pi.poslovna.model.MessageTypes;
import com.pi.poslovna.model.users.User;
import com.pi.poslovna.service.AnalyticsOfStatementService;
import com.pi.poslovna.service.BankAccountService;
import com.pi.poslovna.service.DailyAccountBalanceService;
import com.pi.poslovna.service.InterbankTransferService;
import com.pi.poslovna.service.MedjubankarskiTransferXMLWriterService;
import com.pi.poslovna.service.PrenosXMLReaderService;
import com.pi.poslovna.service.UserService;

@Transactional
@Service
public class PrenosXMLReaderServiceImpl  implements PrenosXMLReaderService{

	@Autowired
	private AnalyticsOfStatementService analyticService;
	
	@Autowired
	private BankAccountService accountService;
	
	@Autowired
	private DailyAccountBalanceService balanceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InterbankTransferService inbankService;
	
	@Autowired
	private MedjubankarskiTransferXMLWriterService XMLWriterService;
	
	@Override
	public void readPrenosXML(String filePath, Principal principal) {
	
		try {
			
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			
			AnalyticsOfStatement analitika = new AnalyticsOfStatement();
			AnalyticsOfStatement analitika2 = new AnalyticsOfStatement();
			
			NodeList nalogodavac = doc.getElementsByTagName("podaci_o_nalogodavcu");
			
			Node nalo = nalogodavac.item(0);
			
			//Nalogodavac (Uplatioc)
			if(nalo.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) nalo;
				analitika.setDebtor(elem.getElementsByTagName("naziv_nalogodavca").item(0).getTextContent());
				analitika2.setDebtor(elem.getElementsByTagName("naziv_nalogodavca").item(0).getTextContent());
			}
			
			//Svrha placanja
			Node svrha = doc.getElementsByTagName("svrha_placanja").item(0);
			analitika.setPurposeOfPayment(svrha.getTextContent());
			analitika2.setPurposeOfPayment(svrha.getTextContent());
			//Primaoc
			NodeList primaoc = doc.getElementsByTagName("podaci_o_primaocu");
			Node prim = primaoc.item(0);
			
			if(prim.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) prim;
				analitika.setRecipient(elem.getElementsByTagName("naziv_primaoca").item(0).getTextContent());
				analitika2.setRecipient(elem.getElementsByTagName("naziv_primaoca").item(0).getTextContent());
				
			}
			
			//Podaci o prenosu
			NodeList prenos = doc.getElementsByTagName("podaci_o_prenosu");
			Node pren = prenos.item(0);
			
			if(pren.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) pren;
				
				//Iznos
				float sum = Float.parseFloat(elem.getElementsByTagName("iznos").item(0).getTextContent());
				analitika.setSum(sum);
				analitika2.setSum(sum);
				
				//Racun nalogodavca (platioca)
				analitika.setDebtorAccount(elem.getElementsByTagName("racun_nalogodavca").item(0).getTextContent());
				analitika2.setDebtorAccount(elem.getElementsByTagName("racun_nalogodavca").item(0).getTextContent());
				
				//Model zaduzenja
				int modelAssigments = Integer.parseInt(elem.getElementsByTagName("model_zaduzenja").item(0).getTextContent());
				analitika.setModelAssigments(modelAssigments);
				analitika2.setModelAssigments(modelAssigments);
				
				//Poziv na broj zaduzenja
				analitika.setReferenceNumberAssigments(elem.getElementsByTagName("poziv_na_broj_zaduznje").item(0).getTextContent());
				analitika2.setReferenceNumberAssigments(elem.getElementsByTagName("poziv_na_broj_zaduznje").item(0).getTextContent());
				
				//Racun primaoca
				analitika.setAccountRecipient(elem.getElementsByTagName("racun_primaoca").item(0).getTextContent());
				analitika2.setAccountRecipient(elem.getElementsByTagName("racun_primaoca").item(0).getTextContent());
				
				//Model odobrenja
				int modelApproval =Integer.parseInt(elem.getElementsByTagName("model_odobrenja").item(0).getTextContent());
				analitika.setModelApproval(modelApproval);
				analitika2.setModelApproval(modelApproval);
				
				
				//Poziv na broj odobrenja
				analitika.setReferenceNumberApproval(elem.getElementsByTagName("poziv_na_broj_odobrenje").item(0).getTextContent());
				analitika2.setReferenceNumberApproval(elem.getElementsByTagName("poziv_na_broj_odobrenje").item(0).getTextContent());
				
			}
			
			//Podaci o prijemu
			NodeList prijem = doc.getElementsByTagName("podaci_o_prijemu");
			Node prij = prijem.item(0);
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			
			if(prij.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element)  prij;
				Date dateOfReceipt = format.parse(elem.getElementsByTagName("datum_prijema").item(0).getTextContent());
				analitika.setDateOfReceipt(dateOfReceipt);
				analitika2.setDateOfReceipt(dateOfReceipt);
				
			}
			else {
				System.out.println("Nema datuma...");
			}
			
			//Datum valute (Datum izvrsenja)
			Node datumValute = doc.getElementsByTagName("datum_valute").item(0);
			
			if(doc.getElementsByTagName("datum_valute").item(0).getTextContent() != "") {
				Date currencyDate = format.parse(datumValute.getTextContent());
				analitika.setCurrencyDate(currencyDate);
				analitika2.setCurrencyDate(currencyDate);
			}
			else {
				System.out.println("Nema datuma...");
			}
			
			//Hitno
			boolean hitno = Boolean.parseBoolean(doc.getElementsByTagName("hitno").item(0).getTextContent());
			analitika.setEmergency(hitno);
			analitika2.setEmergency(hitno);
			
			BankAccount founded = accountService.findByAccountNumber(analitika.getAccountRecipient());
			DailyAccountBalance found = balanceService.findByTrafficDateAndRacun(analitika.getDateOfReceipt(), founded);
			
			if(found == null) {
				BankAccount racun = accountService.findByAccountNumber(analitika.getAccountRecipient());
				DailyAccountBalance nadjeniPoRacunu = balanceService.findByRacun(racun);
				
				boolean medjubankarski = false;
				
				User user = userService.getUserByEmail(principal.getName());
				Bank bank = user.getBank();
				if(bank.getId().equals(racun.getBank().getId())) {
					medjubankarski = false;
				}
				else {
					medjubankarski = true;
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
				
				if(!racun.getMojiDnevniBalansi().contains(dab))
					racun.getMojiDnevniBalansi().add(dab);
				balanceService.save(dab);
				analyticService.save(analitika);
				
				if(medjubankarski) {
					
					Float iznos = analitika.getSum();
					InterbankTransfer it = new InterbankTransfer();
					//RTGS	
					if(analitika.isEmergency() || iznos > 250000f) {
						
						it.setDateIT(analitika.getDateOfReceipt());
						it.setReceiverBank(racun.getBank());
						it.setSenderBank(bank);
						it.setTypeOfMessage(MessageTypes.MT103);
						//ovde baca null pointer treba u modelu vrv promeniti nesto kod liste
						it.getAnalytics().add(analitika);
						inbankService.save(it);
						XMLWriterService.createRTGSXML(it);

					}
					//KLIRING
					else {
						
						it.setDateIT(analitika.getDateOfReceipt());
						it.setReceiverBank(racun.getBank());
						it.setSenderBank(bank);
						it.setTypeOfMessage(MessageTypes.MT102);
						//ovde baca null pointer treba u modelu vrv promeniti nesto kod liste
						it.getAnalytics().add(analitika);
						inbankService.save(it);
					}
				
				}
				}
				else {
				
				if(found.getRacun() == founded) {
					
					//System.out.println("Found racun:"  + found.getRacun().getAccountNumber());
					//System.out.println("Iz analitike:" + founded.getAccountNumber());
					
					boolean medjubankarski = false;
					
					User user = userService.getUserByEmail(principal.getName());
					Bank bank = user.getBank();
					
					if(bank.getId().equals(found.getRacun().getBank().getId())) {
						medjubankarski = false;
					}
					else {
						medjubankarski = true;
					}
					
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
					BankAccount racun = accountService.findByAccountNumber(analitika.getAccountRecipient());
					//staro stanje je stanje sa racuna a novo suma starog prihoda i gubitka
					//found.setPreviousState(Float.parseFloat(racun.getMoney()));
	
					//setujemo novo stanje i na racun
					racun.setMoney(found.getNewState().toString());
					
					found.setRacun(racun);
					analitika.setDnevnoStanjeIzvoda(found);
					found.getMojeAnalitike().add(analitika);
					
					if(!racun.getMojiDnevniBalansi().contains(found))
						racun.getMojiDnevniBalansi().add(found);
					
					if(medjubankarski) {
						
						Float iznos = analitika.getSum();
						InterbankTransfer it = new InterbankTransfer();
						//RTGS
						if(analitika.isEmergency() || iznos > 250000f) {
							
							it.setDateIT(analitika.getDateOfReceipt());
							it.setReceiverBank(racun.getBank());
							it.setSenderBank(bank);
							it.setTypeOfMessage(MessageTypes.MT103);
							//ovde baca null pointer treba u modelu vrv promeniti nesto kod liste
							it.getAnalytics().add(analitika);
							inbankService.save(it);
							XMLWriterService.createRTGSXML(it);
						}
						//KLIRING
						else {
							
							it.setDateIT(analitika.getDateOfReceipt());
							it.setReceiverBank(racun.getBank());
							it.setSenderBank(bank);
							it.setTypeOfMessage(MessageTypes.MT102);
							//ovde baca null pointer treba u modelu vrv promeniti nesto kod liste
							it.getAnalytics().add(analitika);
							inbankService.save(it);
						}
					}
					
					balanceService.save(found);
					//analyticsService.save(analitika);
					
				}
	
			}
			
			
			BankAccount founded2 = accountService.findByAccountNumber(analitika2.getDebtorAccount());
			DailyAccountBalance found2 = balanceService.findByTrafficDateAndRacun(analitika2.getDateOfReceipt(), founded2);
			
			if(found2 == null) {
				BankAccount racun = accountService.findByAccountNumber(analitika2.getDebtorAccount());
				DailyAccountBalance nadjeniPoRacunu = balanceService.findByRacun(racun);
				
				boolean medjubankarski = false;
				
				User user = userService.getUserByEmail(principal.getName());
				Bank bank = user.getBank();
				if(bank.getId().equals(racun.getBank().getId())) {
					medjubankarski = false;
				}
				else {
					medjubankarski = true;
				}
				
				//kreiramo novo dnevno stanje racuna za svaku analitiku
				DailyAccountBalance dab = new DailyAccountBalance();
				//Nisam siguran da li je ovo traffic date
				dab.setTrafficDate(analitika2.getDateOfReceipt());
				//prihod je suma uplate iz analitike
				dab.setTrafficToBenefit(0.0f);
				//gubitak je u slucaju naloga za uplatu 0
				dab.setTrafficToTheBurden(analitika2.getSum());
				//nadjemo taj racun iz analitike
				//staro stanje je stanje sa racuna a novo suma starog prihoda i gubitka
				//dab.setPreviousState(Float.parseFloat(racun.getMoney()));
				if(nadjeniPoRacunu != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String datFound = sdf.format(nadjeniPoRacunu.getTrafficDate());
					String datAnalitika = sdf.format(analitika2.getDateOfReceipt());
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
				analitika2.setDnevnoStanjeIzvoda(dab);
				dab.getMojeAnalitike().add(analitika2);
				
				if(!racun.getMojiDnevniBalansi().contains(dab))
					racun.getMojiDnevniBalansi().add(dab);
				balanceService.save(dab);
				analyticService.save(analitika2);
				
				if(medjubankarski) {
					
					Float iznos = analitika2.getSum();
					InterbankTransfer it = new InterbankTransfer();
					//RTGS	
					if(analitika.isEmergency() || iznos > 250000f) {
						
						it.setDateIT(analitika2.getDateOfReceipt());
						//Nisam siguran da li bi ovde sad trebalo da je obrnuto receiver i sender jer je isplata
						it.setReceiverBank(bank);
						it.setSenderBank(racun.getBank());
						it.setTypeOfMessage(MessageTypes.MT103);
						//ovde baca null pointer treba u modelu vrv promeniti nesto kod liste
						it.getAnalytics().add(analitika2);
						inbankService.save(it);
						XMLWriterService.createRTGSXML(it);

					}
					//KLIRING
					else {
						
						it.setDateIT(analitika2.getDateOfReceipt());
						it.setReceiverBank(bank);
						it.setSenderBank(racun.getBank());
						it.setTypeOfMessage(MessageTypes.MT102);
						//ovde baca null pointer treba u modelu vrv promeniti nesto kod liste
						it.getAnalytics().add(analitika2);
						inbankService.save(it);
					}
				
				}
				}
				else {
				
				if(found2.getRacun() == founded2) {
					
					//System.out.println("Found racun:"  + found.getRacun().getAccountNumber());
					//System.out.println("Iz analitike:" + founded.getAccountNumber());
					boolean medjubankarski = false;
					
					User user = userService.getUserByEmail(principal.getName());
					Bank bank = user.getBank();
					
					if(bank.getId().equals(found.getRacun().getBank().getId())) {
						medjubankarski = false;
					}
					else {
						medjubankarski = true;
					}
					
					found2.setTrafficDate(analitika2.getDateOfReceipt());
					//prihod je suma uplate iz analitike
					found2.setTrafficToBenefit(0.0f);
					//gubitak je u slucaju naloga za uplatu 0
					found2.setTrafficToTheBurden(analitika2.getSum());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String datFound = sdf.format(found2.getTrafficDate());
					String datAnalitika = sdf.format(analitika2.getDateOfReceipt());
					Date datumFound = sdf.parse(datFound);
					Date datumAnalitika = sdf.parse(datAnalitika);
				
					Float upamtiPrethodnoStanje = 0.0f;
					
					if(found2.getPreviousState() == 0.0f) {
						found2.setPreviousState(0.0f);
					}
					else{
						
						upamtiPrethodnoStanje = found2.getPreviousState();
						found2.setPreviousState(0.0f);
					}
					//found.setPreviousState(0.0f);
					if(datumFound.compareTo(datumAnalitika) == 0) {
						
						//System.out.println(datumFound + "equals " + datumAnalitika);
						found2.setNewState(found2.getNewState() + found2.getPreviousState() + found2.getTrafficToBenefit() - found2.getTrafficToTheBurden());
						if(upamtiPrethodnoStanje != 0.0f)
						found2.setPreviousState(upamtiPrethodnoStanje);
						
						
					}
					/*
					else {
						found.setPreviousState(0.0f);
						found.setNewState(found.getPreviousState() + found.getTrafficToBenefit() - found.getTrafficToTheBurden());
						
					}*/
					//nadjemo taj racun iz analitike
					BankAccount racun = accountService.findByAccountNumber(analitika2.getDebtorAccount());
					//staro stanje je stanje sa racuna a novo suma starog prihoda i gubitka
					//found.setPreviousState(Float.parseFloat(racun.getMoney()));

					//setujemo novo stanje i na racun
					racun.setMoney(found2.getNewState().toString());
					
					found2.setRacun(racun);
					analitika2.setDnevnoStanjeIzvoda(found2);
					found2.getMojeAnalitike().add(analitika2);
					if(!racun.getMojiDnevniBalansi().contains(found2))
						racun.getMojiDnevniBalansi().add(found2);
					/*
					if(medjubankarski) {
						
						Float iznos = analitika2.getSum();
						InterbankTransfer it = new InterbankTransfer();
						//RTGS
						if(analitika2.isEmergency() || iznos > 250000f) {
							
							it.setDateIT(analitika2.getDateOfReceipt());
							it.setReceiverBank(bank);
							it.setSenderBank(racun.getBank());
							it.setTypeOfMessage(MessageTypes.MT103);
							//ovde baca null pointer treba u modelu vrv promeniti nesto kod liste
							it.getAnalytics().add(analitika2);
							inbankService.save(it);
							XMLWriterService.createRTGSXML(it);
						}
						//KLIRING
						else {
							
							it.setDateIT(analitika2.getDateOfReceipt());
							it.setReceiverBank(bank);
							it.setSenderBank(racun.getBank());
							it.setTypeOfMessage(MessageTypes.MT102);
							//ovde baca null pointer treba u modelu vrv promeniti nesto kod liste
							it.getAnalytics().add(analitika2);
							inbankService.save(it);
						}
					}
					*/
					balanceService.save(found2);
					//analyticsService.save(analitika);
					
				}

			}
			
			/*
			//Primaoc
			
			DailyAccountBalance dabPrimaoc = new DailyAccountBalance();
			dabPrimaoc.setTrafficDate(analitika.getDateOfReceipt());
			dabPrimaoc.setTrafficToBenefit(analitika.getSum());
			dabPrimaoc.setTrafficToTheBurden(0.0f);
			BankAccount racunPrimaoc = accountService.findByAccountNumber(analitika.getAccountRecipient());
			
			dabPrimaoc.setPreviousState(Float.parseFloat(racunPrimaoc.getMoney()));
			dabPrimaoc.setNewState(dabPrimaoc.getPreviousState() + dabPrimaoc.getTrafficToBenefit() - dabPrimaoc.getTrafficToTheBurden());
			racunPrimaoc.setMoney(dabPrimaoc.getNewState().toString());
			
			dabPrimaoc.setRacun(racunPrimaoc);
			balanceService.save(dabPrimaoc);
			analitika.setDnevnoStanjeIzvoda(dabPrimaoc);
			
			//Posiljaoc
			
			DailyAccountBalance dabPosiljaoc = new DailyAccountBalance();
			dabPosiljaoc.setTrafficDate(analitika2.getDateOfReceipt());
			dabPosiljaoc.setTrafficToBenefit(0.0f);
			dabPosiljaoc.setTrafficToTheBurden(analitika2.getSum());
			BankAccount racunPosiljaoc = accountService.findByAccountNumber(analitika2.getDebtorAccount());
			dabPosiljaoc.setPreviousState(Float.parseFloat(racunPosiljaoc.getMoney()));
			dabPosiljaoc.setNewState(dabPosiljaoc.getPreviousState() + dabPosiljaoc.getTrafficToBenefit() - dabPosiljaoc.getTrafficToTheBurden());
			racunPosiljaoc.setMoney(dabPosiljaoc.getNewState().toString());
			
			dabPosiljaoc.setRacun(racunPosiljaoc);
			balanceService.save(dabPosiljaoc);
			analitika2.setDnevnoStanjeIzvoda(dabPosiljaoc);
			
			analyticService.save(analitika);
			analyticService.save(analitika2);
			//Mora se ispraviti analitka izvoda za prenos jer tu treba da ima dva sloga.
			*/
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		
		
		
	}

}
