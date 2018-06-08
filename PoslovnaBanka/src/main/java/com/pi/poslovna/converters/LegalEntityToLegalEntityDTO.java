package com.pi.poslovna.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pi.poslovna.model.clients.LegalEntities;
import com.pi.poslovna.model.dto.LegalEntityDTO;

@Component
public class LegalEntityToLegalEntityDTO  implements Converter<LegalEntities, LegalEntityDTO>{

	@Override
	public LegalEntityDTO convert(LegalEntities source) {
		
		if(source == null) {
			return null;
		}
		
		ModelMapper mapper = new ModelMapper();
		LegalEntityDTO leDTO = mapper.map(source, LegalEntityDTO.class);
		
		return leDTO;
	}

	public List<LegalEntityDTO> convert(List<LegalEntities> source) {
		
		List<LegalEntityDTO> leDTO = new ArrayList<LegalEntityDTO>();
		
		for(LegalEntities le:source) {
			leDTO.add(convert(le));
		}
		
		return leDTO;
	}
	
}
