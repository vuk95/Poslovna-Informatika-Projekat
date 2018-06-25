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

@Entity(name = "Fizicka_lica")
public class Individuals {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "individual_id")
	private Long Id;
	
	@Column(name = "Ime", columnDefinition="VARCHAR(40)")
	private String name;
	
	@Column(name = "Prezime", columnDefinition="VARCHAR(40)")
	private String lastname;
	
	@Column(name = "JMBG")
	private String jmbg;
	
	@Column(name = "Mesto", columnDefinition="VARCHAR(40)")
	private String place;
	
	@Column(name = "Adresa", columnDefinition="VARCHAR(40)")
	private String address;
	
	@Column(name = "Email", columnDefinition="VARCHAR(40)")
	private String email;
	
	@Column(name = "Telefon", columnDefinition="VARCHAR(40)")
	private String phone;
	
	@Column(name = "Tip", columnDefinition="VARCHAR(40)")
	private ClientType clientType;
	
	@ManyToOne
	@JoinColumn(name = "bank_id")
	@JsonIgnore
	private Bank bank;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "mojiRacuniIndividual",
				joinColumns = @JoinColumn(name = "individual_id", referencedColumnName = "individual_id"),
				inverseJoinColumns = @JoinColumn(name = "racun_id", referencedColumnName = "racun_id"))
	private List<BankAccount> mojiRacuni = new ArrayList<BankAccount>();
	
	public Individuals() {
		
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

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
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

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public List<BankAccount> getMojiRacuni() {
		return mojiRacuni;
	}

	public void setMojiRacuni(List<BankAccount> mojiRacuni) {
		this.mojiRacuni = mojiRacuni;
	}
	
}