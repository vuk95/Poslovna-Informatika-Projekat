package com.pi.poslovna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.model.clients.LegalEntities;
import com.pi.poslovna.service.LegalEntityService;

@RestController
@RequestMapping(value = "/legalEntity")
public class LegalEntityController {

	@Autowired
	private LegalEntityService leService;
	
	@RequestMapping(value = "getEntities" , method = RequestMethod.GET)
	public ResponseEntity<List<LegalEntities>> getEntities() {
		
		List<LegalEntities> le = leService.findAll();
		
		return new ResponseEntity<>(le,HttpStatus.OK);
	}
	
	
}
