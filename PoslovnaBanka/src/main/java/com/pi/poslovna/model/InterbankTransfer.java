package com.pi.poslovna.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity(name = "Medjubankarski_prenos")
public class InterbankTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private MessageTypes typeOfMessage; //MT102-Kliring i MT103-RTGS
	
	private Date date;
	
	@ManyToOne
	private Bank senderBank;
	
	@ManyToOne
	private Bank receiverBank;

	
	@OneToMany
	private List<AnalyticsOfStatement> analytics; //lista stavki za prenos
	
	
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

	public List<AnalyticsOfStatement> getAnalytics() {
		return analytics;
	}

	public void setAnalytics(List<AnalyticsOfStatement> analytics) {
		this.analytics = analytics;
	}
	
	
	
}
