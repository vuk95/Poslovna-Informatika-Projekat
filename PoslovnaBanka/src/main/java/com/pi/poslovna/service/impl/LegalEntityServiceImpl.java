package com.pi.poslovna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.clients.LegalEntities;
import com.pi.poslovna.repository.LegalEntityRepository;
import com.pi.poslovna.service.LegalEntityService;

@Transactional
@Service
public class LegalEntityServiceImpl implements LegalEntityService {

	@Autowired
	private LegalEntityRepository repository;
	
	@Override
	public LegalEntities save(LegalEntities le) {
		return repository.save(le);
	}

	@Override
	public LegalEntities findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<LegalEntities> findAll() {
		return repository.findAll();
	}

	@Override
	public LegalEntities delete(Long id) {
		
	LegalEntities le = repository.findOne(id);
	
		if(le == null) {
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant legal entity");
		}
		
		repository.delete(le);
		
		return le; 
	}

	@Override
	public List<LegalEntities> findLegalEntities(String name, String responsiblePerson, String pib, String place,
			String address, String email, String phone, String fax) {
		
		return repository.findByNameIgnoreCaseContainingAndResponsiblePersonIgnoreCaseContainingAndPibIgnoreCaseContainingAndPlaceIgnoreCaseContainingAndAddressIgnoreCaseContainingAndEmailIgnoreCaseContainingAndPhoneIgnoreCaseContainingAndFaxIgnoreCaseContaining(name, responsiblePerson, pib, place, address, email, phone, fax);
	}

}
