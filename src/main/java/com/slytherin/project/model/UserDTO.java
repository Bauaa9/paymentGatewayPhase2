package com.slytherin.project.model;

import org.springframework.stereotype.Component;

@Component
public class UserDTO {
	private int customer_id;
	private String username;
	private String password;

	public UserDTO() {
		super();
	}

	public UserDTO(int id, String username, String password) {
		super();
		this.customer_id = id;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return customer_id;
	}

	public void setId(int id) {
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