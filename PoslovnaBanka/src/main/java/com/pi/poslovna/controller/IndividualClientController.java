package com.pi.poslovna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.model.clients.Individuals;
import com.pi.poslovna.service.IndividualClientService;

@RestController
@RequestMapping(value = "/individual")
public class IndividualClientController {

	@Autowired
	private IndividualClientService service;
	
	@RequestMapping(value = "/addIndividuals" ,method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Individuals> addIndividuals(@RequestBody Individuals i) {
		
			Individuals individual	=  service.save(i);
		
		return new ResponseEntity<>(individual,HttpStatus.OK);
	}
}
