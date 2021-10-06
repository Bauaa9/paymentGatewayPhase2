package com.slytherin.project.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Id;
	
@Entity
@Table(name="customer_data")
public class CustomerProfileData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int customer_data_id;
	@Column(name="customer_first_name")
	String firstName;
	@Column(name="customer_last_name")
	String lastName;
	@Column(name="customer_email")
	String email;
	@Column(name="customer_contactno")
	long phoneNo;
	
		public CustomerProfileData() {
		// TODO Auto-generated constructor stub
	}
		
 
 public CustomerProfileData(int id, String firstName, String lastName, String email, long phoneNo) {
		super();
		this.customer_data_id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNo = phoneNo;
	}


public long getId() {
	return customer_data_id;
}


public void setId(int id) {
	this.customer_data_id = id;
}


public String getFirstName() {
	return firstName;
}


public void setFirstName(String firstName) {
	this.firstName = firstName;
}


public String getLastName() {
	return lastName;
}


public void setLastName(String lastName) {
	this.lastName = lastName;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}


public long getPhoneNo() {
	return phoneNo;
}


public void setPhoneNo(long phoneNo) {
	this.phoneNo = phoneNo;
}

 
}
