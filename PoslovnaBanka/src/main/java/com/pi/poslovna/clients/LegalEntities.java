package com.pi.poslovna.clients;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Pravna_lica")
public class LegalEntities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "Naziv" , nullable = false)
	private String name;
	
	@Column(name = "Odgovorno_lice" , nullable = false)
	private String responsiblePerson;
	
	@Column(name = "Poreski_idenifikacioni_broj" , nullable = false)
	private int PIB;
	
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

	public int getPIB() {
		return PIB;
	}

	public void setPIB(int pIB) {
		PIB = pIB;
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
	
	
}
