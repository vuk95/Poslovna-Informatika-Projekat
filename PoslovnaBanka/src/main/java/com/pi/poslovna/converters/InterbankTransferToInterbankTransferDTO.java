package com.pi.poslovna.converters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pi.poslovna.model.InterbankTransfer;
import com.pi.poslovna.model.dto.InterbankTransferDTO;


@Component
public class InterbankTransferToInterbankTransferDTO implements Converter<InterbankTransfer, InterbankTransferDTO>{

	@Override
	public InterbankTransferDTO convert(InterbankTransfer source) {
		
		if(source == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		
		String datum = df.format(source.getDateIT());
		
		ModelMapper modelMapper = new ModelMapper();
		InterbankTransferDTO acountDTO = modelMapper.map(source, InterbankTransferDTO.class);
		acountDTO.setDateIT(datum);
		
		return acountDTO;
	}
	
	public List<InterbankTransferDTO> convert(List<InterbankTransfer> source) {
		
		
		List<InterbankTransferDTO> accountDTO =  new ArrayList<InterbankTransferDTO>();
		for(InterbankTransfer account: source) {
			
			accountDTO.add(convert(account));
		}
		
		return accountDTO;
	}

}
