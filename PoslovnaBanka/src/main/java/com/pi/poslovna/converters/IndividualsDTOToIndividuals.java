package com.pi.poslovna.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pi.poslovna.model.clients.Individuals;
import com.pi.poslovna.model.dto.IndividualsDTO;

@Component
public class IndividualsDTOToIndividuals implements Converter<IndividualsDTO,Individuals>{

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
