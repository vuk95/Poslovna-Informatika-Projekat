package com.pi.poslovna.model.dto;

import java.util.Date;

import javax.persistence.Column;

public class DailyAccountBalanceDTO {
	
	private Long id;
	
	private Date trafficDate;
	
	private float previousState;
	
	private Float trafficToBenefit;
	
	private Float trafficToTheBurden;
	
	private Float newState;

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
	
	public DailyAccountBalanceDTO() {}

	public DailyAccountBalanceDTO(Date trafficDate, float previousState, Float trafficToBenefit,
			Float trafficToTheBurden, Float newState) {
		super();
		this.trafficDate = trafficDate;
		this.previousState = previousState;
		this.trafficToBenefit = trafficToBenefit;
		this.trafficToTheBurden = trafficToTheBurden;
		this.newState = newState;
	}

}
