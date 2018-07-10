package com.pi.poslovna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.Currency;
import com.pi.poslovna.repository.CurrencyRepository;
import com.pi.poslovna.service.CurrencyService;

@Transactional
@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Override
	public Currency findOne(Long id) {
		return currencyRepository.findOne(id);
	}

	@Override
	public List<Currency> findAll() {
		return currencyRepository.findAll();
	}

}
