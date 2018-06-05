package com.pi.poslovna.service;

import java.util.List;

import com.pi.poslovna.model.clients.Individuals;

public interface IndividualClientService {
	
	public Individuals save(Individuals i);
	public Individuals findOne(Long id);
	public List<Individuals> findAll();
	public Individuals delete(Long id);
	//public List<Individuals> findIndividuals(String name,String lastname,String jmbg,String place,String address,String email,String phone);
	
	
}
