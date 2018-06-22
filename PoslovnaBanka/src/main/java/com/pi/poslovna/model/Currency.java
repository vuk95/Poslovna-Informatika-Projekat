package com.pi.poslovna.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Valuta")
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Zvanicna_sifra")
	private String official_code;

	@Column(name = "Naziv")
	private String name;
	
	private Boolean domicilna;
	
	@ManyToOne
	private Country country;
	
	@OneToMany
	private Set<CurrencyRate> currencyRates;
	
	public Currency() {
		
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOfficial_code() {
		return official_code;
	}

	public void setOfficial_code(String official_code) {
		this.official_code = official_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDomicilna() {
		return domicilna;
	}

	public void setDomicilna(Boolean domicilna) {
		this.domicilna = domicilna;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Set<CurrencyRate> getCurrencyRates() {
		return currencyRates;
	}

	public void setCurrencyRates(Set<CurrencyRate> currencyRates) {
		this.currencyRates = currencyRates;
	}
	
	
	
}
