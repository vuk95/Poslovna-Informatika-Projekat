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

import com.pi.poslovna.converters.IndividualsDTOToIndividuals;
import com.pi.poslovna.converters.IndividualsToIndividualsDTO;
import com.pi.poslovna.model.clients.Individuals;
import com.pi.poslovna.model.dto.IndividualsDTO;
import com.pi.poslovna.service.IndividualClientService;

@RestController
@RequestMapping(value = "/individual")
public class IndividualClientController {

	@Autowired
	private IndividualClientService clientService;
	
	@Autowired
	private IndividualsToIndividualsDTO toIndividualsDTO;
	
	@Autowired
	private IndividualsDTOToIndividuals toIndividuals;
	
	
	@RequestMapping(value="getIndividuals", method = RequestMethod.GET)
	public ResponseEntity<List<IndividualsDTO>> getIndividuals() {

		List<Individuals> individuals = clientService.findAll();

		return new ResponseEntity<>(toIndividualsDTO.convert(individuals), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<?> addIndividuals(@Validated @RequestBody IndividualsDTO individualDTO, Errors errors){
		
		if(errors.hasErrors()) {
			return new ResponseEntity<String>(errors.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
		}
		
		Individuals newIndividual = clientService.save(toIndividuals.convert(individualDTO));
		return new ResponseEntity<>(toIndividualsDTO.convert(newIndividual), HttpStatus.OK);
	}
}
