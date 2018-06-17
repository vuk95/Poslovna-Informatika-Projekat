package com.pi.poslovna.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Racun")
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Broj_racuna")
	private String accountNumber;
	
	@Column(name = "Datum_otvaranja")
	private Date openingDate;
	
	@Column(name = "Vazeci")
	private boolean active;
	
	@Column(name = "Raspoloziva_sredstva")
	private String money; 
		
	@OneToMany
	@Column(name = "Valuta")
	private Set<Currency> currencies;
	
	@ManyToOne
	private Bank bank;

	
	//PODACI O KLIJENTU
/*	private String ime;
	private String prezime;
	private String naziv;
	private int jmbg;
	private int pib;
	private String adresa;
	private String mesto;
	private CllientType type;
	
*/	
	
	public BankAccount() {
		
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Set<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(Set<Currency> currencies) {
		this.currencies = currencies;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	

	
	
}
