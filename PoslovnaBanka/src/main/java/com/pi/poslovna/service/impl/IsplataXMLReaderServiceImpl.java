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
import com.pi.poslovna.service.AnalyticsOfStatementService;
import com.pi.poslovna.service.IsplataXMLReaderService;


@Transactional
@Service
public class IsplataXMLReaderServiceImpl  implements IsplataXMLReaderService{

	@Autowired
	private AnalyticsOfStatementService analyticServise;
	
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
			
			//TO DO: fali iznos
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
			
			analyticServise.save(analitika);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
