package com.pi.poslovna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.converters.LegalEntityDTOToLegalEntity;
import com.pi.poslovna.converters.LegalEntityToLegalEntityDTO;
import com.pi.poslovna.model.clients.LegalEntities;
import com.pi.poslovna.model.dto.LegalEntityDTO;
import com.pi.poslovna.service.LegalEntityService;

@RestController
@RequestMapping(value = "/legalEntity")
public class LegalEntityController {

	@Autowired
	private LegalEntityService leService;
	
	@Autowired
	private LegalEntityToLegalEntityDTO toLegalEntityDTO;
	
	@Autowired
	private LegalEntityDTOToLegalEntity toLegalEntity;
	
	@RequestMapping(value = "getEntities" , method = RequestMethod.GET)
	public ResponseEntity<List<LegalEntityDTO>> getEntities() {
		
		List<LegalEntities> le = leService.findAll();
		
		return new ResponseEntity<>(toLegalEntityDTO.convert(le),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<LegalEntityDTO> getEntity(@PathVariable Long id) {
		
		LegalEntities le = leService.findOne(id);
		
		if(le == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		
		return new ResponseEntity<>(toLegalEntityDTO.convert(le),HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<?> addLegalEntities(@Validated @RequestBody LegalEntityDTO leDTO, Errors errors){
		
		if(errors.hasErrors()) {
			return new ResponseEntity<String>(errors.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
		}
		
		LegalEntities newLE = leService.save(toLegalEntity.convert(leDTO));
		return new ResponseEntity<>(toLegalEntityDTO.convert(newLE), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
	public ResponseEntity<LegalEntityDTO> deleteLegalEntity(@PathVariable Long id) {
		
		LegalEntities deletedLE = leService.delete(id); 
		
		return new ResponseEntity<>(toLegalEntityDTO.convert(deletedLE),HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT , consumes="application/json" )
	public ResponseEntity<?> updateLegalEntity(@RequestBody LegalEntityDTO leDTO) {
		
		LegalEntities le = leService.findOne(toLegalEntity.convert(leDTO).getId());
		
		le.setName(toLegalEntity.convert(leDTO).getName());
		le.setResponsiblePerson(toLegalEntity.convert(leDTO).getResponsiblePerson());
		le.setPib(toLegalEntity.convert(leDTO).getPib());
		le.setPlace(toLegalEntity.convert(leDTO).getPlace());
		le.setAddress(toLegalEntity.convert(leDTO).getAddress());
		le.setEmail(toLegalEntity.convert(leDTO).getEmail());
		le.setPhone(toLegalEntity.convert(leDTO).getPhone());
		le.setFax(toLegalEntity.convert(leDTO).getFax());
		
		
		leService.save(le);
		
		return new ResponseEntity<>(toLegalEntityDTO.convert(le),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findEntities", method=RequestMethod.GET, consumes="application/json")
	public ResponseEntity<List<LegalEntityDTO>> find(@RequestParam(value = "name", required = false, defaultValue = "") String name, 
			@RequestParam(value = "responsiblePerson", required = false, defaultValue = "") String responsiblePerson, @RequestParam(value = "pib", required = false, defaultValue = "") String pib,
			@RequestParam(value = "place", required = false, defaultValue = "") String place,
			@RequestParam(value = "address", required = false, defaultValue = "") String address, @RequestParam(value = "email", required = false, defaultValue = "") String email,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone, @RequestParam(value = "fax", required = false, defaultValue = "") String fax){
		
		List<LegalEntities> le = leService.findLegalEntities(name, responsiblePerson, pib, place, address, email, phone, fax);
		
		return new ResponseEntity<>(toLegalEntityDTO.convert(le),HttpStatus.OK);
	}
}
