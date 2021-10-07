package com.slytherin.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.slytherin.project.model.ModelTransaction;

@Repository
public interface TransactionDao extends JpaRepository<ModelTransaction, Integer>{

	@Query(value="select trs_id,status,payment_method,merchant_name,date_time,expenditure from transactions where (Date(date_time) between ?1 and ?2)", nativeQuery=true)
	public List<ModelTransaction> findUnBilledTransactions(String lastStmtDate,String nextStmtDate);
	
	@Query(value="select DATE_ADD((DATE_ADD(?1, INTERVAL 1 MONTH)), INTERVAL -1 DAY)"
			, nativeQuery=true)
	public String findNextStmtDate(String id);
	
	@Query(value="select trs_id,status,payment_method,merchant_name,date_time,expenditure from transactions where (Date(date_time) between ?2 and ?1)", nativeQuery=true)
	public List<ModelTransaction> findBilledTransactions(String lastStmtDate,String previousStmtDate);
	
	@Query(value="select sum(expenditure) from transactions where (Date(date_time) between ?1 and ?2);", nativeQuery=true)
	public Float getTotalOutstandingAmount(String lastStmtDate,String nextStmtDate);
	
	@Query(value="select sum(expenditure) from transactions where (Date(date_time) between ?2 and ?1);", nativeQuery=true)
	public Float getTotalAmountDue(String lastStmtDate,String previousStmtDate);
	
	@Query(value="Select DATE_ADD((DATE_SUB(?1, INTERVAL 1 MONTH)), INTERVAL 1 DAY);"
			,nativeQuery=true)
	public String findPreviousStmtDate(String id);
	
	@Query(value="select trs_id,status,payment_method,merchant_name,date_time,expenditure from transactions", nativeQuery=true)
	public List<ModelTransaction> findRetailTransactions();
}
