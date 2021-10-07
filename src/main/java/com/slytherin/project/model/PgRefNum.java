package com.slytherin.project.model;

public class PgRefNum {
	
	String pgRefId;
	String merchantName;
	
	
	public PgRefNum() {
		// TODO Auto-generated constructor stub
	}

	

	public PgRefNum(String pgRefId, String merchantName) {
		super();
		this.pgRefId = pgRefId;
		this.merchantName = merchantName;
	}



	public String getMerchantName() {
		return merchantName;
	}



	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}



	public String getPgRefId() {
		return pgRefId;
	}

	public void setPgRefId(String pgRefId) {
		this.pgRefId = pgRefId;
	}
	
	
}