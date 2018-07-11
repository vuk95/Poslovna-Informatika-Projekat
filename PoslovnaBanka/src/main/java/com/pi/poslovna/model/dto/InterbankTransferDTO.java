package com.pi.poslovna.model.dto;

import java.util.Date;

import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.MessageTypes;

public class InterbankTransferDTO {
	
	private Long id;

	private MessageTypes typeOfMessage; 
	
	private String dateIT;
	
	private Bank senderBank;
	
	private Bank receiverBank;

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

	public String getDateIT() {
		return dateIT;
	}

	public void setDateIT(String dateIT) {
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
	
	public InterbankTransferDTO() {}

	public InterbankTransferDTO(MessageTypes typeOfMessage, String dateIT, Bank senderBank, Bank receiverBank) {
		super();
		this.typeOfMessage = typeOfMessage;
		this.dateIT = dateIT;
		this.senderBank = senderBank;
		this.receiverBank = receiverBank;
	}

}
