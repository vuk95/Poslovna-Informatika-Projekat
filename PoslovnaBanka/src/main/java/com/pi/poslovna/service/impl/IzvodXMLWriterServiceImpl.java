package com.pi.poslovna.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

import com.pi.poslovna.model.AnalyticsOfStatement;
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
	public void createIzvodXML(BankAccount bankAccount, String path) {
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String danas = sdf.format(Calendar.getInstance().getTime()).toString();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("izvod_klijenta");
			doc.appendChild(rootElement);
			
			Element zaglavlje = doc.createElement("zaglavlje_preseka");
			rootElement.appendChild(zaglavlje);
			
			Element broj_racuna = doc.createElement("broj_racuna");
			broj_racuna.appendChild(doc.createTextNode(bankAccount.getAccountNumber()));
			zaglavlje.appendChild(broj_racuna);
			
			Element datum = doc.createElement("datum");	
			datum.appendChild(doc.createTextNode(danas));
			zaglavlje.appendChild(datum);
			
			DailyAccountBalance dnevnoStanje = dnevnoStanjeService.findByTrafficDateAndRacun(sdf.parse(danas), bankAccount);
			if(dnevnoStanje == null) {
				Element status = doc.createElement("status");
				status.appendChild(doc.createTextNode("nema izmena ovog dana"));
				zaglavlje.appendChild(status);
			} else {			
				Element stanje = doc.createElement("stanje");
				zaglavlje.appendChild(stanje);
			
				Element prethodno_stanje = doc.createElement("prethodno_stanje");
				prethodno_stanje.appendChild(doc.createTextNode(Float.toString(dnevnoStanje.getPreviousState())));
				stanje.appendChild(prethodno_stanje);
				
				Element novo_stanje = doc.createElement("novo_stanje");
				novo_stanje.appendChild(doc.createTextNode(Float.toString(dnevnoStanje.getNewState())));
				stanje.appendChild(novo_stanje);
				
				for(AnalyticsOfStatement analitika : dnevnoStanje.getMojeAnalitike()) {
					Element stavka_preseka = doc.createElement("stavka_preseka");
					
					Element duznik = doc.createElement("duznik");
					if(analitika.getDebtor() != null) {
						duznik.appendChild(doc.createTextNode(analitika.getDebtor()));
					}
					stavka_preseka.appendChild(duznik);
					
					Element svrha = doc.createElement("svrha_placanja");
					if(analitika.getPurposeOfPayment() != null) {
						svrha.appendChild(doc.createTextNode(analitika.getPurposeOfPayment()));
					}
					stavka_preseka.appendChild(svrha);
					
					Element primalac = doc.createElement("primalac");
					if(analitika.getRecipient() != null) {
						primalac.appendChild(doc.createTextNode(analitika.getRecipient()));
					}
					stavka_preseka.appendChild(primalac);
					
					Element dNalog = doc.createElement("datum_prijema");
					if(analitika.getDateOfReceipt() != null) {
						dNalog.appendChild(doc.createTextNode(sdf.format(analitika.getDateOfReceipt())));
					}
					stavka_preseka.appendChild(dNalog);
					
					Element dValute = doc.createElement("datum_valute");
					if(analitika.getCurrencyDate() != null) {
						dValute.appendChild(doc.createTextNode(sdf.format(analitika.getCurrencyDate())));
					}
					stavka_preseka.appendChild(dValute);
					
					Element racunD = doc.createElement("racun_duznika");
					if(analitika.getDebtorAccount() != null) {
						racunD.appendChild(doc.createTextNode(analitika.getDebtorAccount()));
					}
					stavka_preseka.appendChild(racunD);
					
					Element modelZ = doc.createElement("model_zaduzenja");
					modelZ.appendChild(doc.createTextNode(Integer.toString(analitika.getModelAssigments())));
					stavka_preseka.appendChild(modelZ);
					
					Element pozivZ = doc.createElement("poziv_na_broj_zaduzenja");
					if(analitika.getReferenceNumberAssigments() != null) {
						pozivZ.appendChild(doc.createTextNode(analitika.getReferenceNumberAssigments()));
					}
					stavka_preseka.appendChild(pozivZ);
					
					Element racunP = doc.createElement("racun_poverioca");
					if(analitika.getAccountRecipient() != null) {
						racunP.appendChild(doc.createTextNode(analitika.getAccountRecipient()));
					}
					stavka_preseka.appendChild(racunP);
					
					Element modelO = doc.createElement("model_odobrenja");
					modelO.appendChild(doc.createTextNode(Integer.toString(analitika.getModelApproval())));
					stavka_preseka.appendChild(modelO);
					
					Element pozivO = doc.createElement("poziv_na_broj_odobrenja");
					if(analitika.getReferenceNumberApproval() != null) {
						pozivO.appendChild(doc.createTextNode(analitika.getReferenceNumberApproval()));
					}
					stavka_preseka.appendChild(pozivO);
					
					Element iznos = doc.createElement("iznos");
					iznos.appendChild(doc.createTextNode(Float.toString(analitika.getSum())));
					stavka_preseka.appendChild(iznos);
					
					rootElement.appendChild(stavka_preseka);
				}
			}
			
			String filePath = path + "izvod.xml";
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			
			transformer.transform(source, result);
			
			System.out.println("Izvod je sacuvan: [ " + filePath + "]");
		} catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch(TransformerException tfe) {
			tfe.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
