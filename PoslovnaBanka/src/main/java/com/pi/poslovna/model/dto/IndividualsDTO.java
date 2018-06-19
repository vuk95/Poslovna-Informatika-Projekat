package com.pi.poslovna.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.pi.poslovna.model.clients.ClientType;


public class IndividualsDTO {
	
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String lastname;
	@NotNull
	@Size(min=13, max=13, message="JMBG mora imati tacno 13 karaktera")
	@Pattern(regexp="^(0|[1-9][0-9]*)$")
	private String jmbg;
	@NotEmpty
	private String place;
	@NotEmpty
	private String address;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	private String phone;
	
	private ClientType type;
	
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

	public ClientType getType() {
		return type;
	}
	public void setType(ClientType type) {
		this.type = type;
	}
	
	public IndividualsDTO(String name, String lastname, String jmbg, String place, String address, String email,
			String phone, ClientType type) {
		
		this.name = name;
		this.lastname = lastname;
		this.jmbg = jmbg;
		this.place = place;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.type = type;
	}
	
	public IndividualsDTO() {
		
	}
	
}
