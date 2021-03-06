package com.pi.poslovna.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.clients.ClientType;
import com.pi.poslovna.model.clients.LegalEntities;
import com.pi.poslovna.model.dto.LegalEntityDTO;
import com.pi.poslovna.service.BankService;

@Component
public class LegalEntityDTOToLegalEntity implements Converter<LegalEntityDTO, LegalEntities> {

	@Autowired
	private BankService bankService;
	
	@Override
	public LegalEntities convert(LegalEntityDTO source) {
		
		if(source == null) {
			return null;
		}
		
		LegalEntities entities = new LegalEntities();
		entities.setId(source.getId());
		entities.setName(source.getName());
		entities.setResponsiblePerson(source.getResponsiblePerson());
		entities.setPib(source.getPib());
		entities.setPlace(source.getPlace());
		entities.setAddress(source.getAddress());
		entities.setEmail(source.getEmail());
		entities.setPhone(source.getPhone());
		entities.setFax(source.getFax());
		entities.setClientType(ClientType.LEGAL_ENTITY);
		
		if(source.getBankId() != null) {
			Bank bank = bankService.findOne(source.getBankId());
			entities.setBankLE(bank);
		}
		
		return entities;
	}

	
	public List<LegalEntities> convert(List<LegalEntityDTO> source) {
		
		List<LegalEntities> entities = new ArrayList<LegalEntities>();	
		
		for(LegalEntityDTO leDTO: source) {
			entities.add(convert(leDTO));
		}		
				
		return entities;
	}
	
}
