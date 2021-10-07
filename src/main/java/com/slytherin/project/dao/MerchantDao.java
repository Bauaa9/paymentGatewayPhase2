package com.slytherin.project.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.slytherin.project.model.DAOMerchant;

@Repository
public interface MerchantDao extends CrudRepository<DAOMerchant, Integer>  {
	
	DAOMerchant findByUsername(String username);
	
}