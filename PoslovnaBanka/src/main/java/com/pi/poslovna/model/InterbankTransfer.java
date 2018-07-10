package com.pi.poslovna.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity(name = "Medjubankarski_prenos")
public class InterbankTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String typeOfMessage; ////MT102 i MT103
	
	private Date date;
	
	@ManyToOne
	private Bank senderBank;
	
	@ManyToOne
	private Bank receiverBank;

	//TO DO: Ukljuciti analitike izvoda - preko posebene klase (stavke prenosa) ili direktno ovde
	
	public InterbankTransfer() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeOfMessage() {
		return typeOfMessage;
	}

	public void setTypeOfMessage(String typeOfMessage) {
		this.typeOfMessage = typeOfMessage;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Bank getSenderBank() {
		return senderBank;
	}

	public void setSenderBank(Bank senderBank) {
		this.senderBank = senderBank;
	}

	public Bank getReceiverBank() {
		return receiverBank;
	}

	public void setReceiverBank(Bank receiverBank) {
		this.receiverBank = receiverBank;
	}
	
	
	
}
