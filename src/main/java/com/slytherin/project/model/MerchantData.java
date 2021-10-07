package com.slytherin.project.model;

/**
 * @author Tejas Abhang
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "merchant_data")
public class MerchantData {
	@Id
	@Column(name="merchant_id")
	String merchantId;
	
	@Column(name="secret_key")
	String secretKey;
	
	@Column(name = "merchant_name")
	String merchantName;
	
	@Column(name = "merchant_category")
	String merchantCategory;
	
	public MerchantData() {
		// TODO Auto-generated constructor stub
	}
	
	public MerchantData(String merchantId, String secretKey, String merchantName, String merchantCategory) {
		super();
		this.merchantId = merchantId;
		this.secretKey = secretKey;
		this.merchantName = merchantName;
		this.merchantCategory = merchantCategory;
	}

	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantCategory() {
		return merchantCategory;
	}

	public void setMerchantCategory(String merchantCategory) {
		this.merchantCategory = merchantCategory;
	}
	
}
