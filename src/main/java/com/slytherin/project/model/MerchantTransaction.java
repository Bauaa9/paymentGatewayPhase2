package com.slytherin.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "merchant_transaction_details")
public class MerchantTransaction {
	@Id
	@Column(name="pg_ref_id")
	String pgRefId;
	
	@Column(name="merchant_id")
	String merchantId;
	
	@Column(name="payment_type")
	String paymentType;
	
	@Column(name="total_amount")
	int totalAmount;
	
	@Column(name="date")
	String dataTime;
	
	@Column(name="order_id")
	String orderId;
	
	String status;
	
	public MerchantTransaction() {
		// TODO Auto-generated constructor stub
	}

	public MerchantTransaction(String pgRefId, String merchantId, String paymentType, int totalAmount,
			String dataTime, String orderId, String status) {
		super();
		this.pgRefId = pgRefId;
		this.merchantId = merchantId;
		this.paymentType = paymentType;
		this.totalAmount = totalAmount;
		this.dataTime = dataTime;
		this.orderId = orderId;
		this.status = status;
	}

	public String getPgRefId() {
		return pgRefId;
	}

	public void setPgRefId(String pgRefId) {
		this.pgRefId = pgRefId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
