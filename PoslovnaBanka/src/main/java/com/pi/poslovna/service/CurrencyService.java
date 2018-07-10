package com.pi.poslovna.service;

import java.util.List;

import com.pi.poslovna.model.Currency;

public interface CurrencyService {

	public Currency findOne(Long id);
	public List<Currency> findAll();
}
