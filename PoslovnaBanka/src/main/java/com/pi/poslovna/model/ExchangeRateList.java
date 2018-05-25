package com.pi.poslovna.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Kursna_lista")
public class ExchangeRateList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Datum")
	private Date date;
	
	@Column(name = "Broj_kursne_Liste")
	private int numberOfExchangeRateList;
	
	@Column(name = "Primenjuje_se_od")
	private Date appliedBy;
	
	public ExchangeRateList() {
		
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumberOfExchangeRateList() {
		return numberOfExchangeRateList;
	}

	public void setNumberOfExchangeRateList(int numberOfExchangeRateList) {
		this.numberOfExchangeRateList = numberOfExchangeRateList;
	}

	public Date getAppliedBy() {
		return appliedBy;
	}

	public void setAppliedBy(Date appliedBy) {
		this.appliedBy = appliedBy;
	}
	
	
}
