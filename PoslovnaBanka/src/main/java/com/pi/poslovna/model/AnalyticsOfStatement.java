package com.pi.poslovna.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Analitika_izvoda")
public class AnalyticsOfStatement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Duznik")
	private String debtor;
	
	@Column(name = "Svrha_placanja")
	private String purposeOfPayment; 
	
	@Column(name = "Primalac")
	private String recipient;
	
	@Column(name = "Datum_prijema")
	private Date dateOfReceipt;
	
	@Column(name = "Datum_valute")
	private Date currencyDate;
	
	@Column(name = "Racun_duznika")
	private String debtorAccount;
	
	@Column(name = "Model_zaduzenja")
	private int modelAssigments;
	
	@Column(name = "Poziv_na_broj_zaduzenja")
	private String referenceNumberAssigments;
	
	@Column(name = "Racun_primaoca")
	private String accountRecipient;
	
	@Column(name = "Model_odobrenja")
	private int modelApproval;
	
	@Column(name = "Poziv_na_broj_odobrenja")
	private String referenceNumberApproval;
	
	@Column(name = "Hitno")
	private boolean emergency;
	
	@Column(name = "Iznos")
	private float sum;
	
	@Column(name = "Tip_greske")
	private int typeOfMistake;
	
	@Column(name = "Status")
	private String status;
	
	
	public AnalyticsOfStatement() {
		
	}


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


	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}


	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}


	public Date getCurrencyDate() {
		return currencyDate;
	}


	public void setCurrencyDate(Date currencyDate) {
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
	
	
}
