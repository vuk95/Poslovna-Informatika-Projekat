package com.pi.poslovna.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.pi.poslovna.model.clients.Individuals;
import com.pi.poslovna.model.clients.LegalEntities;

@Entity(name = "Banka")
public class Bank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "Oznaka")
	private String code;
	
	@Column(name = "Poreski_idenifikacioni_broj")
	private int PIB;
	
	@Column(name = "Ime")
	private String name;
	
	@Column(name = "Adresa")
	private String address;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Telefon")
	private String phone;
	
	@Column(name = "Fax")
	private String fax;
	
	@OneToMany(
			mappedBy = "bank",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Individuals> individualClients = new ArrayList<>();
	
	@OneToMany(
			mappedBy = "bankLE",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<LegalEntities> legalEntityClients = new ArrayList<>();
	
	public Bank() {
		
	}

	public Bank(String code,int PIB,String name,String address,String email,String phone,String fax){
		
		this.code = code;
		this.PIB = PIB;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.fax = fax;
	}
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getPIB() {
		return PIB;
	}

	public void setPIB(int pIB) {
		PIB = pIB;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Individuals> getIndividualClients() {
		return individualClients;
	}

	public void setIndividualClients(List<Individuals> individualClients) {
		this.individualClients = individualClients;
	}

	public List<LegalEntities> getLegalEntityClients() {
		return legalEntityClients;
	}

	public void setLegalEntityClients(List<LegalEntities> legalEntityClients) {
		this.legalEntityClients = legalEntityClients;
	}

}
