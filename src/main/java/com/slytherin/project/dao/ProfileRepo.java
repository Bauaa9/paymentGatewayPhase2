package com.slytherin.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.slytherin.project.model.CustomerProfileData;

@Repository
public interface ProfileRepo extends JpaRepository<CustomerProfileData,Integer> {
	
	@Query(value = "select customer_data_id,customer_first_name,customer_last_name,customer_email,customer_contactno from customer_data where customer_id=?1",nativeQuery = true)
	public CustomerProfileData getUserDetailsById(int id);
}
