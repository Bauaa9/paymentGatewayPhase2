package com.slytherin.project.model;

public class MerchantlastWeekData {
	
	String date;
	int transaction;
	
	public MerchantlastWeekData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MerchantlastWeekData(String date, int transaction) {
		super();
		this.date = date;
		this.transaction = transaction;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTransaction() {
		return transaction;
	}

	public void setTransaction(int transaction) {
		this.transaction = transaction;
	}

	@Override
	public String toString() {
		return "lastWeekData [date=" + date + ", transaction=" + transaction + "]";
	}
	
	
	

}
