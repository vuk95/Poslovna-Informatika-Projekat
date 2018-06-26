package com.pi.poslovna.model.clients;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.BankAccount;

@Entity(name = "Pravna_lica")
public class LegalEntities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "legal_id")
	private Long Id;
	
	@Column(name = "Naziv" , nullable = false)
	private String name;
	
	@Column(name = "Odgovorno_lice" , nullable = false)
	private String responsiblePerson;
	
	@Column(name = "Poreski_identifikacioni_broj" , nullable = false)
	private String pib;
	
	@Column(name = "Mesto" , nullable = false)
	private String place;
	
	@Column(name = "Adresa" , nullable = false)
	private String address;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Telefon")
	private String phone;
	
	@Column(name = "Fax")
	private String fax;
	
	@Column(name = "Tip")
	private ClientType clientType;
	
	@ManyToOne
	@JoinColumn(name = "bank_id")
	@JsonIgnore
	private Bank bankLE;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "mojiRacuniLegal",
				joinColumns = @JoinColumn(name = "legal_id", referencedColumnName = "legal_id"),
				inverseJoinColumns = @JoinColumn(name = "racun_id", referencedColumnName = "racun_id"))
	private List<BankAccount> mojiRacuni = new ArrayList<BankAccount>();
	
	public LegalEntities() {
		
	}
	
	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	
	public String getPib() {
		return pib;
	}



	public void setPib(String pib) {
		this.pib = pib;
	}


	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public Bank getBankLE() {
		return bankLE;
	}

	public void setBankLE(Bank bankLE) {
		this.bankLE = bankLE;
	}

	public List<BankAccount> getMojiRacuni() {
		return mojiRacuni;
	}

	public void setMojiRacuni(List<BankAccount> mojiRacuni) {
		this.mojiRacuni = mojiRacuni;
	}
	
}
