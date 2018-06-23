package com.pi.poslovna.converters;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.dto.BankAccountDTO;

@Component
public class BankAccountToBankAccountDTO  implements Converter<BankAccount, BankAccountDTO>{

	@Override
	public BankAccountDTO convert(BankAccount source) {
		
		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		BankAccountDTO acountDTO = modelMapper.map(source, BankAccountDTO.class);
		
		return acountDTO;
	}

	public List<BankAccountDTO> convert(List<BankAccount> source) {
		
		List<BankAccountDTO> accountDTO =  new ArrayList<BankAccountDTO>();
		for(BankAccount account: source) {
			accountDTO.add(convert(account));
		}
		
		return accountDTO;
	}
	
}
