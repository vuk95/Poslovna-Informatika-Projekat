package com.pi.poslovna.model.dto;

import java.util.Date;

public class AnalyticsOfStatementDTO {
	
	private Long id;
	
	private String debtor;
	
	private String purposeOfPayment; 
	
	private String recipient;
	
	private String dateOfReceipt;
	
	private String currencyDate;
	
	private String debtorAccount;
	
	private int modelAssigments;
	
	private String referenceNumberAssigments;
	
	private String accountRecipient;
	
	private int modelApproval;
	
	private String referenceNumberApproval;
	
	private boolean emergency;
	
	private float sum;
	
	private int typeOfMistake;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDebtor() {
		return debtor;
	}

	public void setDebtor(String debtor) {
		this.debtor = debtor;
	}

	public String getPurposeOfPayment() {
		return purposeOfPayment;
	}

	public void setPurposeOfPayment(String purposeOfPayment) {
		this.purposeOfPayment = purposeOfPayment;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getDateOfReceipt() {
		return dateOfReceipt;
	}

	public void setDateOfReceipt(String dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public String getCurrencyDate() {
		return currencyDate;
	}

	public void setCurrencyDate(String currencyDate) {
		this.currencyDate = currencyDate;
	}

	public String getDebtorAccount() {
		return debtorAccount;
	}

	public void setDebtorAccount(String debtorAccount) {
		this.debtorAccount = debtorAccount;
	}

	public int getModelAssigments() {
		return modelAssigments;
	}

	public void setModelAssigments(int modelAssigments) {
		this.modelAssigments = modelAssigments;
	}

	public String getReferenceNumberAssigments() {
		return referenceNumberAssigments;
	}

	public void setReferenceNumberAssigments(String referenceNumberAssigments) {
		this.referenceNumberAssigments = referenceNumberAssigments;
	}

	public String getAccountRecipient() {
		return accountRecipient;
	}

	public void setAccountRecipient(String accountRecipient) {
		this.accountRecipient = accountRecipient;
	}

	public int getModelApproval() {
		return modelApproval;
	}

	public void setModelApproval(int modelApproval) {
		this.modelApproval = modelApproval;
	}

	public String getReferenceNumberApproval() {
		return referenceNumberApproval;
	}

	public void setReferenceNumberApproval(String referenceNumberApproval) {
		this.referenceNumberApproval = referenceNumberApproval;
	}

	public boolean isEmergency() {
		return emergency;
	}

	public void setEmergency(boolean emergency) {
		this.emergency = emergency;
	}

	public float getSum() {
		return sum;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}

	public int getTypeOfMistake() {
		return typeOfMistake;
	}

	public void setTypeOfMistake(int typeOfMistake) {
		this.typeOfMistake = typeOfMistake;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AnalyticsOfStatementDTO(String debtor, String purposeOfPayment, String recipient, String dateOfReceipt,
			String currencyDate, String debtorAccount, int modelAssigments, String referenceNumberAssigments,
			String accountRecipient, int modelApproval, String referenceNumberApproval, boolean emergency, float sum,
			int typeOfMistake, String status) {
		super();
		this.debtor = debtor;
		this.purposeOfPayment = purposeOfPayment;
		this.recipient = recipient;
		this.dateOfReceipt = dateOfReceipt;
		this.currencyDate = currencyDate;
		this.debtorAccount = debtorAccount;
		this.modelAssigments = modelAssigments;
		this.referenceNumberAssigments = referenceNumberAssigments;
		this.accountRecipient = accountRecipient;
		this.modelApproval = modelApproval;
		this.referenceNumberApproval = referenceNumberApproval;
		this.emergency = emergency;
		this.sum = sum;
		this.typeOfMistake = typeOfMistake;
		this.status = status;
	}
	
	public AnalyticsOfStatementDTO() {
		
	}
	

}
