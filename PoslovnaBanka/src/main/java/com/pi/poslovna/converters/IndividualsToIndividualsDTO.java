package com.pi.poslovna.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pi.poslovna.model.clients.Individuals;
import com.pi.poslovna.model.dto.IndividualsDTO;

@Component
public class IndividualsToIndividualsDTO implements Converter<Individuals, IndividualsDTO>{

	@Override
	public IndividualsDTO convert(Individuals source) {
		// TODO Auto-generated method stub
		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		IndividualsDTO individualDTO = modelMapper.map(source, IndividualsDTO.class);
		return individualDTO;
	}
	
	public List<IndividualsDTO> convert(List<Individuals> source){
		
		List<IndividualsDTO> individualsDTO = new ArrayList<IndividualsDTO>();
		for (Individuals individual : source) {
			individualsDTO.add(convert(individual));
		}
		
		return individualsDTO;
	}

}
