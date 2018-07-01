package com.pi.poslovna.service.impl;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import com.pi.poslovna.service.UplataXMLReaderService;

@Transactional
@Service
public class UplataXMLReaderServiceImpl implements UplataXMLReaderService {

	@Override
	public void readUplataXML() {
		try {
			File xmlFile = new File("C:\\Users\\User\\Documents\\GitHub\\Poslovna-Informatika-Projekat\\PoslovnaBanka\\src\\main\\resources\\nalozi\\NalogZaUplatu.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("podaci_o_uplatiocu");
		
			Node nNode = nList.item(0);
			System.out.println(nNode.getNodeName());
				
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				System.out.println("Naziv uplatioca: " + eElement.getElementsByTagName("naziv_uplatioca").item(0).getTextContent());
				NodeList adresa = eElement.getElementsByTagName("adresa");
				Node adresaNode = adresa.item(0);
				Element adresaElement = (Element) adresaNode;
				System.out.println("Adresa - mesto: " + adresaElement.getElementsByTagName("mesto").item(0).getTextContent());
				System.out.println("Adresa - ulica: " + adresaElement.getElementsByTagName("ulica").item(0).getTextContent());
				System.out.println("Adresa - broj: " + adresaElement.getElementsByTagName("broj").item(0).getTextContent());
				System.out.println("Adresa - postanski broj: " + adresaElement.getElementsByTagName("postanski_broj").item(0).getTextContent());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
