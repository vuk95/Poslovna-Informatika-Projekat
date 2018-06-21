package com.pi.poslovna.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.clients.ClientType;
import com.pi.poslovna.model.clients.Individuals;
import com.pi.poslovna.model.dto.IndividualsDTO;
import com.pi.poslovna.service.BankService;

@Component
public class IndividualsDTOToIndividuals implements Converter<IndividualsDTO,Individuals>{

	@Autowired
	private BankService bankService;
	
	@Override
	public Individuals convert(IndividualsDTO source) {
		
		if(source == null) {
			return null;
		}
		
		Individuals individual = new Individuals();
		individual.setId(source.getId());
		individual.setName(source.getName());
		individual.setLastname(source.getLastname());
		individual.setJmbg(source.getJmbg());
		individual.setPlace(source.getPlace());
		individual.setPhone(source.getPhone());
		individual.setEmail(source.getEmail());
		individual.setAddress(source.getAddress());
		individual.setClientType(ClientType.INDIVIDUAL);
		
		if(source.getBankId() != null) {
			Bank bank = bankService.findOne(source.getBankId());
			individual.setBank(bank);
		}
		
		return individual;
	}
	
	public List<Individuals> convert(List<IndividualsDTO> source){
		
		List<Individuals> individuals = new ArrayList<Individuals>();
		for(IndividualsDTO individualsDTO : source) {
			individuals.add(convert(individualsDTO));
		}
		
		return individuals;
		
	}

}
