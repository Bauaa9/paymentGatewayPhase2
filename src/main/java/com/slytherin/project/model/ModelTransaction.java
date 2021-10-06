package com.slytherin.project.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="transactions")
public class ModelTransaction {

	@Id
	int trs_id;
	
	@Column
	String status;
	
	@Column
	LocalDateTime date_time;
	
	@Column
	String payment_method;
	
	@Column
	String merchant_name;
	
	@Column
	float expenditure;

	public ModelTransaction() {
		super();
	}

	public ModelTransaction(int trs_id, String status, LocalDateTime date_time, String payment_method,
			String merchant_name, float expenditure) {
		super();
		this.trs_id = trs_id;
		this.status = status;
		this.date_time = date_time;
		this.payment_method = payment_method;
		this.merchant_name = merchant_name;
		this.expenditure = expenditure;
	}

	public int getTrs_id() {
		return trs_id;
	}

	public void setTrs_id(int trs_id) {
		this.trs_id = trs_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getDate_time() {
		return date_time;
	}

	public void setDate_time(LocalDateTime date_time) {
		this.date_time = date_time;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getMerchant_name() {
		return merchant_name;
	}

	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}

	public float getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(float expenditure) {
		this.expenditure = expenditure;
	}
	
}
