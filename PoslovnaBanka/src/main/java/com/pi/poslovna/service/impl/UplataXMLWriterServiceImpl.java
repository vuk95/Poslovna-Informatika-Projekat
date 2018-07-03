package com.pi.poslovna.service.impl;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.pi.poslovna.service.UplataXMLWriterService;

@Transactional
@Service
public class UplataXMLWriterServiceImpl implements UplataXMLWriterService{

	@Override
	public void writeXML() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("nalog_za_prenos");
			doc.appendChild(rootElement);
			
			// podaci o nalogodavcu elements
			Element nalogodavac = doc.createElement("podaci_o_nalogodavcu");
			rootElement.appendChild(nalogodavac);
			
			// naziv elements
			Element naziv = doc.createElement("naziv_nalogodavca");
			naziv.appendChild(doc.createTextNode("Yuto Nagatomo"));
			nalogodavac.appendChild(naziv);
			
			//adresa element
			Element adresa = doc.createElement("adresa");
			nalogodavac.appendChild(adresa);
			
			//mesto elements
			Element mesto = doc.createElement("mesto");
			mesto.appendChild(doc.createTextNode("Japan"));
			adresa.appendChild(mesto);
			
			//mesto elements
			Element ulica = doc.createElement("ulica");
			ulica.appendChild(doc.createTextNode("Katsumi Yamade"));
			adresa.appendChild(ulica);
			
			Element broj = doc.createElement("broj");
			broj.appendChild(doc.createTextNode("55"));
			adresa.appendChild(broj);
			
			Element postal = doc.createElement("postanski_broj");
			postal.appendChild(doc.createTextNode("12345"));
			adresa.appendChild(postal);
			
			// podaci o nalogodavcu elements
			Element whatsthepoint = doc.createElement("svrha_placanja");
			whatsthepoint.appendChild(doc.createTextNode("Bzv"));
			rootElement.appendChild(whatsthepoint);
			
			Element primaoc = doc.createElement("podaci_o_primaocu");
			rootElement.appendChild(primaoc);
			
			Element name = doc.createElement("naziv_primaoca");
			name.appendChild(doc.createTextNode("Maya Yoshida"));
			primaoc.appendChild(name);
			
			Element address = doc.createElement("adresa");
			primaoc.appendChild(address);
			
			Element place = doc.createElement("mesto");
			place.appendChild(doc.createTextNode("Japan"));
			address.appendChild(place);
			
			Element street = doc.createElement("ulica");
			street.appendChild(doc.createTextNode("Shinga Yamamota"));
			address.appendChild(street);
			
			Element number = doc.createElement("broj");
			number.appendChild(doc.createTextNode("6"));
			address.appendChild(number);
			
			Element postanski = doc.createElement("postanski_broj");
			postanski.appendChild(doc.createTextNode("66"));
			address.appendChild(postanski);
			
			Element podaci_prenos = doc.createElement("podaci_o_prenosu");
			rootElement.appendChild(podaci_prenos);
			
			Element sifra = doc.createElement("sifra_placanja");
			sifra.appendChild(doc.createTextNode("111"));
			podaci_prenos.appendChild(sifra);
			
			Element valuta = doc.createElement("valuta");
			valuta.appendChild(doc.createTextNode("DOLLAR"));
			podaci_prenos.appendChild(valuta);
			
			Element iznos = doc.createElement("iznos");
			iznos.appendChild(doc.createTextNode("500000"));
			podaci_prenos.appendChild(iznos);
			
			
			Element racun_nal = doc.createElement("racun_nalogodavca");
			racun_nal.appendChild(doc.createTextNode("123456789012345678"));
			podaci_prenos.appendChild(racun_nal);
			
			Element model_zad = doc.createElement("model");
			model_zad.appendChild(doc.createTextNode("97"));
			podaci_prenos.appendChild(model_zad);
			
			Element poziv_na_br_zad = doc.createElement("poziv_na_broj_zaduzenje");
			poziv_na_br_zad.appendChild(doc.createTextNode("1234564573495643"));
			podaci_prenos.appendChild(poziv_na_br_zad);
			
			Element racun_prim = doc.createElement("racun_primaoca");
			racun_prim.appendChild(doc.createTextNode("567843215678908743"));
			podaci_prenos.appendChild(sifra);
			
			Element model_odobrenja = doc.createElement("model");
			model_odobrenja.appendChild(doc.createTextNode("97"));
			podaci_prenos.appendChild(model_odobrenja);
			
			Element poziv_na_br_odobrenja = doc.createElement("poziv_na_broj_odobrenje");
			poziv_na_br_odobrenja.appendChild(doc.createTextNode("1234564573495643"));
			podaci_prenos.appendChild(poziv_na_br_odobrenja);
			
			Element podaci_o_prijemu = doc.createElement("podaci_o_prijemu");
			rootElement.appendChild(podaci_o_prijemu);
			
			Element mesto_prijema = doc.createElement("mesto_prijema");
			mesto_prijema.appendChild(doc.createTextNode("Japan"));
			podaci_o_prijemu.appendChild(mesto_prijema);
			
			Element datum_prijema = doc.createElement("datum_prijema");
			datum_prijema.appendChild(doc.createTextNode("28-5-2008"));
			podaci_o_prijemu.appendChild(datum_prijema);
			
			Element datum_valute = doc.createElement("datum_valute");
			datum_valute.appendChild(doc.createTextNode("1-1-2008"));
			rootElement.appendChild(datum_valute);
			
			Element urgent = doc.createElement("hitno");
			urgent.appendChild(doc.createTextNode("0"));
			rootElement.appendChild(urgent);
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("yuto.xml"));
			
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");
			
		}
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
