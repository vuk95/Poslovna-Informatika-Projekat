package com.pi.poslovna.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name = "Medjubankarski_prenos")
public class InterbankTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interbank_id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private MessageTypes typeOfMessage; //MT102-Kliring i MT103-RTGS
	
	private Date dateIT;
	
	@ManyToOne
	private Bank senderBank;
	
	@ManyToOne
	private Bank receiverBank;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY) 	
	private List<AnalyticsOfStatement> analytics = new ArrayList<AnalyticsOfStatement>(); //lista stavki za prenos
	
	private boolean exported = false;
	
	public InterbankTransfer() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public MessageTypes getTypeOfMessage() {
		return typeOfMessage;
	}

	public void setTypeOfMessage(MessageTypes typeOfMessage) {
		this.typeOfMessage = typeOfMessage;
	}

	
	public Date getDateIT() {
		return dateIT;
	}

	public void setDateIT(Date dateIT) {
		this.dateIT = dateIT;
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

	public List<AnalyticsOfStatement> getAnalytics() {
		return analytics;
	}

	public void setAnalytics(List<AnalyticsOfStatement> analytics) {
		this.analytics = analytics;
	}

	public boolean isExported() {
		return exported;
	}

	public void setExported(boolean exported) {
		this.exported = exported;
	}
	
}
