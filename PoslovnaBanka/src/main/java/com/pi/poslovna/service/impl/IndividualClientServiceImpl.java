package com.pi.poslovna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.clients.ClientType;
import com.pi.poslovna.model.clients.Individuals;
import com.pi.poslovna.repository.IndividualClientRepository;
import com.pi.poslovna.service.IndividualClientService;

@Transactional
@Service
public class IndividualClientServiceImpl implements IndividualClientService{

	@Autowired
	private IndividualClientRepository repository;

	@Override
	public Individuals save(Individuals i) {
		
		return repository.save(i);
	}

	@Override
	public Individuals findOne(Long id) {
		return repository.findOne(id);
	}
	
	
	@Override
	public List<Individuals> findAll() {
		return repository.findAll();
	}

	@Override
	public Individuals delete(Long id) {
		
	Individuals individual = repository.findOne(id);
	
	if(individual == null) {
		
		throw new IllegalArgumentException("Tried to delete"
				+ "non-existant person");

	}
		repository.delete(individual);
		
		return individual;
	}

	@Override
	public List<Individuals> findIndividuals(String name, String lastname, String jmbg, String place, String address,
			String email, String phone) {
		
		return repository.findByNameIgnoreCaseContainingAndLastnameIgnoreCaseContainingAndJmbgIgnoreCaseContainingAndPlaceIgnoreCaseContainingAndAddressIgnoreCaseContainingAndEmailIgnoreCaseContainingAndPhoneIgnoreCaseContaining(name, lastname, jmbg, place, address, email, phone);
	}


	

	
}
