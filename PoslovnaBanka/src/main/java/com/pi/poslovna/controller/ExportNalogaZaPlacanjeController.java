package com.pi.poslovna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.service.UplataXMLWriterService;

@RestController
public class ExportNalogaZaPlacanjeController {

	@Autowired
	public UplataXMLWriterService uplataXMLWriterService;
	
	@RequestMapping(value = "/kreirajXML", method = RequestMethod.GET)
	public boolean proba() {
		
		uplataXMLWriterService.writeXML();
	
		return true;
	}
	
}
