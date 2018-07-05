package com.pi.poslovna.controller;

import java.io.UnsupportedEncodingException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.service.IsplataXMLReaderService;
import com.pi.poslovna.service.UplataXMLReaderService;

@RestController
public class XMLReaderController {

	@Autowired
	public UplataXMLReaderService uplataXMLReaderService;
	
	@Autowired
	public IsplataXMLReaderService isplataXMLReaderService;
	
	@RequestMapping(value="/import_uplata", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<String> setPathToFile(@RequestBody String filePath) throws UnsupportedEncodingException{
		
		String decoded = java.net.URLDecoder.decode(filePath, "UTF-8");
		String path = decoded.substring(9);
		
		uplataXMLReaderService.readUplataXML(path);
		
		return new ResponseEntity<>(path, HttpStatus.OK);
		 
	}
	
	@RequestMapping(value = "/import_isplata" , method = RequestMethod.POST , consumes="application/json")
	public ResponseEntity<String> setIsplataFilePath(@RequestBody String filePath) throws UnsupportedEncodingException {
	
		String decoded = java.net.URLDecoder.decode(filePath, "UTF-8");
		String path = decoded.substring(9);
		
		isplataXMLReaderService.readIsplataXML(path);
		
		return new ResponseEntity<>(path,HttpStatus.OK);
	}
	
	/*@RequestMapping(value = "/nalog_za_uplatu", method = RequestMethod.GET)
	public boolean proba() {
		uplataXMLReaderService.readUplataXML();
		
		return true;
	}
	*/
	
}
