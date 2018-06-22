package com.pi.poslovna.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Drzava")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Oznaka", unique = true , columnDefinition = "CHAR(3)")
	private String code;
	
	@Column(name = "Naziv")
	private String name;
	
	@OneToMany
	private Set<Currency> currencies;
	
	public Country() {
		
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Set<Currency> getCurrencies() {
		return currencies;
	}


	public void setCurrencies(Set<Currency> currencies) {
		this.currencies = currencies;
	}
	
	
	

}
