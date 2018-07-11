package com.pi.poslovna.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pi.poslovna.model.DailyAccountBalance;
import com.pi.poslovna.model.dto.DailyAccountBalanceDTO;

@Component
public class DailyAccountToDailyAccountDTO implements Converter<DailyAccountBalance, DailyAccountBalanceDTO>{

	@Override
	public DailyAccountBalanceDTO convert(DailyAccountBalance source) {
		
		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		DailyAccountBalanceDTO acountDTO = modelMapper.map(source, DailyAccountBalanceDTO.class);
		
		return acountDTO;
	}
	
	public List<DailyAccountBalanceDTO> convert(List<DailyAccountBalance> source) {
		
		List<DailyAccountBalanceDTO> accountDTO =  new ArrayList<DailyAccountBalanceDTO>();
		for(DailyAccountBalance account: source) {
			accountDTO.add(convert(account));
		}
		
		return accountDTO;
	}

}
