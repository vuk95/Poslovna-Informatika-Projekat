package com.pi.poslovna.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.AnalyticsOfStatement;
import com.pi.poslovna.model.InterbankTransfer;
import com.pi.poslovna.service.MedjubankarskiTransferXMLWriterService;

@Transactional
@Service
public class MedjubankarskiTransferXMLWriterServiceImpl implements MedjubankarskiTransferXMLWriterService {

	@Override
	public void createRTGSXML(InterbankTransfer it) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();		
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("rtgs_nalog");
			doc.appendChild(rootElement);
			
			Element bankaNal = doc.createElement("banka_nalogodavca");
			Element imeNal = doc.createElement("ime_banke");
			imeNal.appendChild(doc.createTextNode(it.getSenderBank().getName()));
			bankaNal.appendChild(imeNal);
			Element pibNal = doc.createElement("pib_banke");
			pibNal.appendChild(doc.createTextNode(Integer.toString(it.getSenderBank().getPIB())));
			bankaNal.appendChild(pibNal);
			rootElement.appendChild(bankaNal);
			Element podaciNal = doc.createElement("podaci_nalogodavca");
			rootElement.appendChild(podaciNal);
			
			Element svrha = doc.createElement("svrha_placanja");
			rootElement.appendChild(svrha);
			
			Element bankaPri = doc.createElement("banka_primaoca");
			Element imePri = doc.createElement("ime_banke");
			imePri.appendChild(doc.createTextNode(it.getReceiverBank().getName()));
			bankaPri.appendChild(imePri);
			Element pibPri = doc.createElement("pib_banke");
			pibPri.appendChild(doc.createTextNode(Integer.toString(it.getReceiverBank().getPIB())));
			bankaPri.appendChild(pibPri);
			rootElement.appendChild(bankaPri);
			Element podaciPri = doc.createElement("podaci_o_primaocu");
			rootElement.appendChild(podaciPri);
			
			for(AnalyticsOfStatement analitika : it.getAnalytics()) {
				Element podaciNalog = doc.createElement("podaci_o_nalogu");
				
				Element duznik = doc.createElement("naziv_nalogodavca");
				if(analitika.getDebtor() != null) {
					duznik.appendChild(doc.createTextNode(analitika.getDebtor()));
				}
				podaciNal.appendChild(duznik);
				
				if(analitika.getPurposeOfPayment() != null) {
					svrha.appendChild(doc.createTextNode(analitika.getPurposeOfPayment()));
				}
				
				Element primalac = doc.createElement("primalac");
				if(analitika.getRecipient() != null) {
					primalac.appendChild(doc.createTextNode(analitika.getRecipient()));
				}
				podaciPri.appendChild(primalac);
				
				Element dNalog = doc.createElement("datum_prijema");
				if(analitika.getDateOfReceipt() != null) {
					dNalog.appendChild(doc.createTextNode(sdf.format(analitika.getDateOfReceipt())));
				}
				podaciNalog.appendChild(dNalog);
				
				Element dValute = doc.createElement("datum_valute");
				if(analitika.getCurrencyDate() != null) {
					dValute.appendChild(doc.createTextNode(sdf.format(analitika.getCurrencyDate())));
				}
				podaciNalog.appendChild(dValute);
				
				Element racunD = doc.createElement("racun_duznika");
				if(analitika.getDebtorAccount() != null) {
					racunD.appendChild(doc.createTextNode(analitika.getDebtorAccount()));
				}
				podaciNal.appendChild(racunD);
				
				Element modelZ = doc.createElement("model_zaduzenja");
				modelZ.appendChild(doc.createTextNode(Integer.toString(analitika.getModelAssigments())));
				podaciNalog.appendChild(modelZ);
				
				Element pozivZ = doc.createElement("poziv_na_broj_zaduzenja");
				if(analitika.getReferenceNumberAssigments() != null) {
					pozivZ.appendChild(doc.createTextNode(analitika.getReferenceNumberAssigments()));
				}
				podaciNalog.appendChild(pozivZ);
				
				Element racunP = doc.createElement("racun_poverioca");
				if(analitika.getAccountRecipient() != null) {
					racunP.appendChild(doc.createTextNode(analitika.getAccountRecipient()));
				}
				podaciPri.appendChild(racunP);
				
				Element modelO = doc.createElement("model_odobrenja");
				modelO.appendChild(doc.createTextNode(Integer.toString(analitika.getModelApproval())));
				podaciNalog.appendChild(modelO);
				
				Element pozivO = doc.createElement("poziv_na_broj_odobrenja");
				if(analitika.getReferenceNumberApproval() != null) {
					pozivO.appendChild(doc.createTextNode(analitika.getReferenceNumberApproval()));
				}
				podaciNalog.appendChild(pozivO);
				
				Element iznos = doc.createElement("iznos");
				iznos.appendChild(doc.createTextNode(Float.toString(analitika.getSum())));
				podaciNalog.appendChild(iznos);
				
				rootElement.appendChild(podaciNalog);
			}

			String rootPath = System.getProperty("user.dir");
			String filePath = rootPath + "/src/main/resources/rtgs/" + "rtgs"+ it.getId().toString() +".xml";
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);
			
			
			System.out.println("RTGS nalog se snimio kao [ " + filePath + " ]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createClearingXML() {
		// TODO Auto-generated method stub
		
	}

}
