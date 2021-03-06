package com.pi.poslovna.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pi.poslovna.model.clients.ClientType;
import com.pi.poslovna.model.clients.Individuals;
import com.pi.poslovna.model.clients.LegalEntities;

@Entity(name = "Racun")
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "racun_id")
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

	@ManyToOne
	private Individuals individual;
	
	@ManyToOne
	private LegalEntities legalEntity;
	
	@OneToMany(
			mappedBy = "bankAccount",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<DeactivateBankAccount> deactivate = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private ClientType clientType;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "mojiDnevniBalansi",
				joinColumns = @JoinColumn(name = "racun_id", referencedColumnName = "racun_id"),
					inverseJoinColumns = @JoinColumn(name = "balance_id", referencedColumnName = "balance_id"))
	@JsonIgnore
	private List<DailyAccountBalance> mojiDnevniBalansi = new ArrayList<DailyAccountBalance>();
	
	//PODACI O KLIJENTU
/*	private String ime;
	private String prezime;
	private String naziv;
	private int jmbg;
	private int pib;
	private String adresa;
	private String mesto;
		
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


	public Individuals getIndividual() {
		return individual;
	}


	public void setIndividual(Individuals individual) {
		this.individual = individual;
	}


	public LegalEntities getLegalEntity() {
		return legalEntity;
	}


	public void setLegalEntity(LegalEntities legalEntity) {
		this.legalEntity = legalEntity;
	}


	public ClientType getClientType() {
		return clientType;
	}


	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}


	public List<DeactivateBankAccount> getDeactivate() {
		return deactivate;
	}


	public void setDeactivate(List<DeactivateBankAccount> deactivate) {
		this.deactivate = deactivate;
	}


	public List<DailyAccountBalance> getMojiDnevniBalansi() {
		return mojiDnevniBalansi;
	}


	public void setMojiDnevniBalansi(List<DailyAccountBalance> mojiDnevniBalansi) {
		this.mojiDnevniBalansi = mojiDnevniBalansi;
	}
	
	
	
}
