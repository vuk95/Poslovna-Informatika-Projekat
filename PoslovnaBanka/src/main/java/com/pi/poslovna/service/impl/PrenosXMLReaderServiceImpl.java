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
import com.pi.poslovna.service.PrenosXMLReaderService;

@Transactional
@Service
public class PrenosXMLReaderServiceImpl  implements PrenosXMLReaderService{

	@Autowired
	private AnalyticsOfStatementService analyticService;
	
	@Autowired
	private BankAccountService accountService;
	
	@Autowired
	private DailyAccountBalanceService balanceService;
	
	@Override
	public void readPrenosXML(String filePath) {
	
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
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		
		
		
	}

}