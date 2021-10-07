package com.slytherin.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "merchant_login")
public class DAOMerchant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long merchant_id;
	@Column
	private String username;
	@Column
	private String password;
 
	public long getId() {
		return merchant_id;
	}

	public void setId(long id) {
		this.merchant_id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
