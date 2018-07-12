package com.pi.poslovna.service.impl;

import java.io.File;
import java.security.Principal;
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

import com.pi.poslovna.model.InterbankTransfer;
import com.pi.poslovna.model.MessageTypes;
import com.pi.poslovna.model.AnalyticsOfStatement;
import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.DailyAccountBalance;
import com.pi.poslovna.model.users.User;
import com.pi.poslovna.service.AnalyticsOfStatementService;
import com.pi.poslovna.service.BankAccountService;
import com.pi.poslovna.service.DailyAccountBalanceService;
import com.pi.poslovna.service.InterbankTransferService;
import com.pi.poslovna.service.MedjubankarskiTransferXMLWriterService;
import com.pi.poslovna.service.UplataXMLReaderService;
import com.pi.poslovna.service.UserService;

@Transactional
@Service
public class UplataXMLReaderServiceImpl implements UplataXMLReaderService {
	
	@Autowired
	private AnalyticsOfStatementService analyticsService;

	@Autowired
	private DailyAccountBalanceService balanceService;
	
	@Autowired
	private BankAccountService accountService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InterbankTransferService inbankService;
	
	@Autowired
	private MedjubankarskiTransferXMLWriterService XMLWriterService;
	
	@Override
	public void readUplataXML(String filePath, Principal principal) {
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
			
			//-----------------------ODAVDE KRECE OBRADA UPLATE-------------------------
			
			BankAccount founded = accountService.findByAccountNumber(analitika.getAccountRecipient());
			//nadjemo racun po datumu i broju racuna
			DailyAccountBalance found = balanceService.findByTrafficDateAndRacun(analitika.getDateOfReceipt(), founded);
			
			
			//ako nije nadjen nijedan kreiraj novo dnevno stanje i novi balans
			if(found == null) {
				
				BankAccount racun = accountService.findByAccountNumber(analitika.getAccountRecipient());
				//OVO CE NAM TREBATI DA VIDIMO DA LI JE PRETHODNIH DANA BILO NESTO ZBOG PREVIOUS STATE
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
				//AKO IMA NESTO PROVERI DA LI JE BILO PRE TRENUTNOG I PRENESI STANJE
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
				//AKO NEMA PRETHODNO STANJE JE 0
				else {
					dab.setPreviousState(0.0f);
				}
				dab.setNewState(dab.getPreviousState() + dab.getTrafficToBenefit() - dab.getTrafficToTheBurden());
				//setujemo novo stanje i na racun
				racun.setMoney(dab.getNewState().toString());
				//SETUJEMO RACUN ZA BALANS
				dab.setRacun(racun);
				//SETUJEMO BALANS ZA ANALITIKU
				analitika.setDnevnoStanjeIzvoda(dab);
	
				dab.getMojeAnalitike().add(analitika);
				
				//MORA BITI UNIQUE DODAJEMO BALANS U LISTU BALANSA U RACUNU
				if(!racun.getMojiDnevniBalansi().contains(dab))
					racun.getMojiDnevniBalansi().add(dab);
				//CUVAMO I BALANS I ANALITIKU
				balanceService.save(dab);
				analyticsService.save(analitika);
				
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
						it.setExported(true);
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
			//---------------A AKO JE NASAO NEKI SA ISTIM DATUMOM I BROJEM-----------
			else {
				
				if(found.getRacun() == founded) {
					
					//provera da li je medjubankarski transfer
					//-----------------------------------------------
					boolean medjubankarski = false;
					
					User user = userService.getUserByEmail(principal.getName());
					Bank bank = user.getBank();
					
					if(bank.getId().equals(found.getRacun().getBank().getId())) {
						medjubankarski = false;
					}
					else {
						medjubankarski = true;
					}
					//------------------------------------------------
					
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
					//POMOCNA PROMENLJIVA
					Float upamtiPrethodnoStanje = 0.0f;
					
					if(found.getPreviousState() == 0.0f) {
						found.setPreviousState(0.0f);
					}
					else{
						upamtiPrethodnoStanje = found.getPreviousState();
						found.setPreviousState(0.0f);
					}
					
					if(datumFound.compareTo(datumAnalitika) == 0) {
						
						found.setNewState(found.getNewState() + found.getPreviousState() + found.getTrafficToBenefit() - found.getTrafficToTheBurden());
						//setuj stanje od prethodnog dana
						if(upamtiPrethodnoStanje != 0.0f)
							found.setPreviousState(upamtiPrethodnoStanje);
						
						
					}
					
					//nadjemo taj racun iz analitike
					BankAccount racun = accountService.findByAccountNumber(analitika.getAccountRecipient());
					
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
							it.setExported(true);
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
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
