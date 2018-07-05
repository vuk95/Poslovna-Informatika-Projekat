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
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import com.pi.poslovna.model.AnalyticsOfStatement;
import com.pi.poslovna.service.AnalyticsOfStatementService;
import com.pi.poslovna.service.UplataXMLReaderService;

@Transactional
@Service
public class UplataXMLReaderServiceImpl implements UplataXMLReaderService {
	
	@Autowired
	private AnalyticsOfStatementService analyticsService;

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
		
			analyticsService.save(analitika);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
