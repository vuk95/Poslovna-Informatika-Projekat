package com.pi.poslovna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.service.UplataXMLReaderService;

@RestController
public class XMLReaderController {

	@Autowired
	public UplataXMLReaderService uplataXMLReaderService;
	
	@RequestMapping(value = "/nalog_za_uplatu", method = RequestMethod.GET)
	public boolean proba() {
		uplataXMLReaderService.readUplataXML();
		
		return true;
	}
	
	
}
