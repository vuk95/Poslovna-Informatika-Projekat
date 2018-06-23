package com.pi.poslovna.model.dto;

import java.sql.Date;

import com.pi.poslovna.model.clients.ClientType;


public class BankAccountDTO {

	private Long id;
	
	private String accountNumber;
	
	private Date openingDate;
	
	private boolean active;
	
	private String money; 
			
	private Long bankId;

	private Long individualId;
	
	private Long legalEntityId;
	
	private ClientType clientType;
	
	
	public BankAccountDTO() {
		
	}
	
	public BankAccountDTO(Long id,String accountNumber,Date openingDate,boolean active,String money,Long bankId,Long individualId,Long legalEntityId,ClientType clientType) {
		
		this.id = id;
		this.accountNumber = accountNumber;
		this.openingDate = openingDate;
		this.active = active;
		this.money = money;
		this.bankId = bankId;
		this.individualId = individualId;
		this.legalEntityId = legalEntityId;
		this.clientType = clientType;
		
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public Date getOpeningDate() {
		return openingDate;
	}


	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public String getMoney() {
		return money;
	}


	public void setMoney(String money) {
		this.money = money;
	}


	public Long getBankId() {
		return bankId;
	}


	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}


	public Long getIndividualId() {
		return individualId;
	}


	public void setIndividualId(Long individualId) {
		this.individualId = individualId;
	}


	public Long getLegalEntityId() {
		return legalEntityId;
	}


	public void setLegalEntityId(Long legalEntityId) {
		this.legalEntityId = legalEntityId;
	}


	public ClientType getClientType() {
		return clientType;
	}


	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}
	
}
