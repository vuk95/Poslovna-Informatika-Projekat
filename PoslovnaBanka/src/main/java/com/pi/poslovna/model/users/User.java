package com.pi.poslovna.model.users;

import javax.persistence.*;

@Entity(name = "Korisnik")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long Id;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "lozinka", nullable = false)
	private String password;
	
	public User() {
		
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
