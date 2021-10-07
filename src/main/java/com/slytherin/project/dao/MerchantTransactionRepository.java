package com.slytherin.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slytherin.project.model.MerchantTransaction;


public interface MerchantTransactionRepository extends JpaRepository<MerchantTransaction, String> {

}
