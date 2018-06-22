package com.pi.poslovna.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "Kurs_u_valuti")
public class CurrencyRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Kupovni")
	private float buyingExchangeRate;
	
	@Column(name = "Srednji")
	private float middleExchangeRate;

	@Column(name = "Prodajni")
	private float sellingExchangeRate;
	
	@ManyToOne
	private Currency currency;
	
	@ManyToOne
	private ExchangeRateList exchangeRateList;
	
	public CurrencyRate() {
		
	}

	public CurrencyRate(float buyingExchangeRate, float middleExchangeRate, float sellingExchangeRate) {
		
		this.buyingExchangeRate = buyingExchangeRate;
		this.middleExchangeRate = middleExchangeRate;
		this.sellingExchangeRate = sellingExchangeRate;
		
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getBuyingExchangeRate() {
		return buyingExchangeRate;
	}

	public void setBuyingExchangeRate(float buyingExchangeRate) {
		this.buyingExchangeRate = buyingExchangeRate;
	}

	public float getMiddleExchangeRate() {
		return middleExchangeRate;
	}

	public void setMiddleExchangeRate(float middleExchangeRate) {
		this.middleExchangeRate = middleExchangeRate;
	}

	public float getSellingExchangeRate() {
		return sellingExchangeRate;
	}

	public void setSellingExchangeRate(float sellingExchangeRate) {
		this.sellingExchangeRate = sellingExchangeRate;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public ExchangeRateList getExchangeRateList() {
		return exchangeRateList;
	}

	public void setExchangeRateList(ExchangeRateList exchangeRateList) {
		this.exchangeRateList = exchangeRateList;
	}
	
	
	
	
}
