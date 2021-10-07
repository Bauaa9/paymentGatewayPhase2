package com.slytherin.project.model;

public class Transaction {

	String secretKey;
	String merchentId;
	String orderId;
	
	public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public Transaction(String secretKey, String merchentId,String orderId) {
		super();
		this.secretKey = secretKey;
		this.merchentId = merchentId;
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getMerchentId() {
		return merchentId;
	}

	public void setMerchentId(String merchentId) {
		this.merchentId = merchentId;
	}


}
