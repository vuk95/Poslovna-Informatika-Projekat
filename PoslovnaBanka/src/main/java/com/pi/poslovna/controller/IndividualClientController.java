package com.pi.poslovna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
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
	private IndividualClientService clientService;
	
	@RequestMapping(value="getIndividuals", method = RequestMethod.GET)
	public ResponseEntity<List<Individuals>> getIndividuals() {

		List<Individuals> individuals = clientService.findAll();

		return new ResponseEntity<>(individuals, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Individuals> addIndividuals(@RequestBody Individuals individual){
		
		Individuals newIndividual = clientService.save(individual);
		return new ResponseEntity<>(newIndividual, HttpStatus.OK);
	}
}
