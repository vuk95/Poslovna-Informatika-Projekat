package com.pi.poslovna.model.users;

import javax.persistence.*;

import com.pi.poslovna.model.Bank;

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
	
	@ManyToOne
	@JoinColumn(name = "bank_id", nullable = false)
	private Bank bank;
	
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

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
}
