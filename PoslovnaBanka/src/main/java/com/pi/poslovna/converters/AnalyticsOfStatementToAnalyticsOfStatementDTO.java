package com.pi.poslovna.converters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pi.poslovna.model.AnalyticsOfStatement;
import com.pi.poslovna.model.dto.AnalyticsOfStatementDTO;


@Component
public class AnalyticsOfStatementToAnalyticsOfStatementDTO implements Converter<AnalyticsOfStatement, AnalyticsOfStatementDTO>{

	@Override
	public AnalyticsOfStatementDTO convert(AnalyticsOfStatement source) {
		
		if(source == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		
		String datum = df.format(source.getDateOfReceipt());
		String datum2 = df.format(source.getCurrencyDate());
		
		ModelMapper modelMapper = new ModelMapper();
		AnalyticsOfStatementDTO analitikaDTO = modelMapper.map(source, AnalyticsOfStatementDTO.class);
		analitikaDTO.setDateOfReceipt(datum);
		analitikaDTO.setCurrencyDate(datum2);
		
		return analitikaDTO;
	}
	
	public List<AnalyticsOfStatementDTO> convert(List<AnalyticsOfStatement> source) {
		
		
		List<AnalyticsOfStatementDTO> analitikaDTO =  new ArrayList<AnalyticsOfStatementDTO>();
		for(AnalyticsOfStatement analitika : source) {
			
			analitikaDTO.add(convert(analitika));
		}
		
		return analitikaDTO;
	}


}
