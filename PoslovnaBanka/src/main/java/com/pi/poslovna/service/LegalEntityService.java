package com.pi.poslovna.service;

import java.util.List;

import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.clients.LegalEntities;

public interface LegalEntityService {

	public LegalEntities save(LegalEntities le);
	public LegalEntities findOne(Long id);
	public List<LegalEntities> findAll();
	public LegalEntities delete(Long id);
	public List<LegalEntities> findLegalEntities(String name,String responsiblePerson,String pib,String place,String address,String email,String phone,String fax);
	public void addBankAccount(BankAccount r, Long Id);
}
