package com.pi.poslovna.service;

import java.util.List;

import com.pi.poslovna.model.clients.Individuals;

public interface IndividualClientService {
	
	public Individuals save(Individuals i);
	public List<Individuals> findAll();
	
}
