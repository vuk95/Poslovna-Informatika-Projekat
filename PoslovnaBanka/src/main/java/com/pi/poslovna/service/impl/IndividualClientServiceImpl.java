package com.pi.poslovna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.clients.Individuals;
import com.pi.poslovna.repository.IndividualClientRepository;
import com.pi.poslovna.service.IndividualClientService;

@Service
public class IndividualClientServiceImpl implements IndividualClientService{

	@Autowired
	private IndividualClientRepository repository;

	@Override
	public Individuals save(Individuals i) {
		return repository.save(i);
	}

	@Override
	public List<Individuals> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	

	
}
