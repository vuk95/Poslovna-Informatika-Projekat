package com.pi.poslovna.clients;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Fizicka_lica")
public class Individuals {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "Ime" , nullable = false)
	private String name;
	
	@Column(name = "Prezime" , nullable = false)
	private String lastname;
	
	@Column(name = "JMBG" , nullable = false)
	private int jmbg;
	
	@Column(name = "Mesto" , nullable = false)
	private String place;
	
	@Column(name = "Adresa" , nullable = false)
	private String address;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Telefon")
	private String phone;
	
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

	public int getJmbg() {
		return jmbg;
	}

	public void setJmbg(int jmbg) {
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
	
	
}
