package com.pi.poslovna.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

@Entity(name = "Dnevno_stanje_racuna")
public class DailyAccountBalance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "balance_id")
	private Long id;
	
	@Column(name = "Datum_prometa")
	private Date trafficDate;
	
	@Column(name = "Prethodno_stanje")
	private float previousState;
	
	@Column(name = "Promet_u_korist")
	private Float trafficToBenefit;
	
	@Column(name = "Promet_na_teret")
	private Float trafficToTheBurden;
	
	@Column(name = "Novo_stanje")
	private Float newState;
	
	@ManyToOne
	private BankAccount racun;
	
	//List Analitika
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "mojeAnalitike",
				joinColumns = @JoinColumn(name = "balance_id", referencedColumnName = "balance_id"),
				inverseJoinColumns = @JoinColumn(name = "analytics_id", referencedColumnName = "analytics_id"))
	private List<AnalyticsOfStatement> mojeAnalitike = new ArrayList<AnalyticsOfStatement>();
	
	public DailyAccountBalance() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTrafficDate() {
		return trafficDate;
	}

	public void setTrafficDate(Date trafficDate) {
		this.trafficDate = trafficDate;
	}

	public float getPreviousState() {
		return previousState;
	}

	public void setPreviousState(float previousState) {
		this.previousState = previousState;
	}

	public Float getTrafficToBenefit() {
		return trafficToBenefit;
	}

	public void setTrafficToBenefit(Float trafficToBenefit) {
		this.trafficToBenefit = trafficToBenefit;
	}

	public Float getTrafficToTheBurden() {
		return trafficToTheBurden;
	}

	public void setTrafficToTheBurden(Float trafficToTheBurden) {
		this.trafficToTheBurden = trafficToTheBurden;
	}

	public Float getNewState() {
		return newState;
	}

	public void setNewState(Float newState) {
		this.newState = newState;
	}

	public BankAccount getRacun() {
		return racun;
	}

	public void setRacun(BankAccount racun) {
		this.racun = racun;
	}

	public List<AnalyticsOfStatement> getMojeAnalitike() {
		return mojeAnalitike;
	}

	public void setMojeAnalitike(List<AnalyticsOfStatement> mojeAnalitike) {
		this.mojeAnalitike = mojeAnalitike;
	}
	
}
