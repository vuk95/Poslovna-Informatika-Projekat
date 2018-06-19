package com.pi.poslovna.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.pi.poslovna.model.clients.ClientType;

public class LegalEntityDTO {

	private Long Id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String responsiblePerson;
	
	@NotNull
	@Size(min=10, max=10, message="PIB mora imati tacno 10 karaktera")
	@Pattern(regexp="^(0|[1-9][0-9]*)$")
	private String pib;
	
	@NotEmpty
	private String place;
	
	@NotEmpty
	private String address;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String phone;
	
	@NotEmpty
	private String fax;
	
	private ClientType type;
	
	public LegalEntityDTO() {
		
	}

	public LegalEntityDTO(String name,String responsiblePerson,String pib,String place,String address,String email,String phone,String fax, ClientType tip) {
		
		this.name = name;
		this.responsiblePerson = responsiblePerson;
		this.pib = pib;
		this.place = place;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.fax = fax;
		this.type = tip;
		
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

	public ClientType getType() {
		return type;
	}

	public void setType(ClientType type) {
		this.type = type;
	}

}
