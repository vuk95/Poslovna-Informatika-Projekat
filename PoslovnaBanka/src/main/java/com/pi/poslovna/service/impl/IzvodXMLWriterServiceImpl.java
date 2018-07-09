package com.pi.poslovna.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.DailyAccountBalance;
import com.pi.poslovna.service.DailyAccountBalanceService;
import com.pi.poslovna.service.IzvodXMLWriterService;

@Transactional
@Service
public class IzvodXMLWriterServiceImpl implements IzvodXMLWriterService {

	@Autowired
	public DailyAccountBalanceService dnevnoStanjeService;
	
	@Override
	public void createIzvodXML(BankAccount bankAccount) {
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String danas = sdf.format(Calendar.getInstance().getTime()).toString();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("izvod_klijenta");
			doc.appendChild(rootElement);
			
			//BROJ RACUNA I DATUM
			Element broj_racuna = doc.createElement("broj_racuna");
			broj_racuna.appendChild(doc.createTextNode(bankAccount.getAccountNumber()));
			rootElement.appendChild(broj_racuna);
			
			Element datum = doc.createElement("datum");	
			datum.appendChild(doc.createTextNode(danas));
			rootElement.appendChild(datum);
			
			//STANJE
			DailyAccountBalance dnevnoStanje = dnevnoStanjeService.findByTrafficDateAndRacun(sdf.parse(danas), bankAccount);
			if(dnevnoStanje == null) {
				Element status = doc.createElement("status");
				status.appendChild(doc.createTextNode("nema izmena ovog dana"));
				rootElement.appendChild(status);
			} else {			
				Element stanje = doc.createElement("stanje");
				rootElement.appendChild(stanje);
			
				Element prethodno_stanje = doc.createElement("prethodno_stanje");
				prethodno_stanje.appendChild(doc.createTextNode(Float.toString(dnevnoStanje.getPreviousState())));
				stanje.appendChild(prethodno_stanje);
				
				Element novo_stanje = doc.createElement("novo_stanje");
				novo_stanje.appendChild(doc.createTextNode(Float.toString(dnevnoStanje.getNewState())));
				stanje.appendChild(novo_stanje);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\Users\\User\\Desktop\\izvod.xml"));
			
			transformer.transform(source, result);
			
			System.out.println("Izvod je sacuvan!");
		} catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch(TransformerException tfe) {
			tfe.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
