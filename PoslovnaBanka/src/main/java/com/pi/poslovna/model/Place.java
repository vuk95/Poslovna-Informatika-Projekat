package com.pi.poslovna.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Naseljeno_mesto")
public class Place {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(name = "Naziv")
	private String name;

	@Column(name = "PTT_oznaka")
	private String pttNumber;

	public Place() {
		
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPttNumber() {
		return pttNumber;
	}

	public void setPttNumber(String pttNumber) {
		this.pttNumber = pttNumber;
	}

}