package com.slytherin.project.dao;

/**
 * @author Tejas Abhang
 */

import org.springframework.data.jpa.repository.JpaRepository;

import com.slytherin.project.model.MerchantData;


public interface MerchantDataRepository extends JpaRepository<MerchantData, String> {

}
