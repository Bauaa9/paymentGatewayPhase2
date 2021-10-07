package com.slytherin.project.model;


import javax.persistence.*;

@Entity
@Table(name = "customer_login")
public class DAOUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customer_id;
	@Column
	private String username;
	@Column
	private String password;
 
	public long getId() {
		return customer_id;
	}

	public void setId(long id) {
		this.customer_id = id;
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