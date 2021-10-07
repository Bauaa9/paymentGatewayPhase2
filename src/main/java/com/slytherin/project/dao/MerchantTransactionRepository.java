package com.slytherin.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.slytherin.project.model.MerchantTransaction;

@Repository
public interface MerchantTransactionRepository extends JpaRepository<MerchantTransaction, String> {

	@Query(value = "Select pg_ref_id,merchant_id,payment_type,total_amount,order_id,status,date from transaction_details t where t.pg_ref_id=?1", nativeQuery = true)
	public List<MerchantTransaction> findStatus(int x);

	// For Today's Transaction
	@Query(value = "SELECT SUM(total_amount)  FROM merchant_transaction_details  where DATE(date) = CURDATE()  AND status='SUCCESS'", nativeQuery = true)
	public int SumForToday();

	@Query(value = "SELECT SUM(total_amount)  FROM merchant_transaction_details  where DATE(date) = CURDATE() AND payment_type='DEBIT' AND status='SUCCESS'", nativeQuery = true)
	public int SumForTodayByDebit();

	@Query(value = "SELECT SUM(total_amount)  FROM merchant_transaction_details  where DATE(date) = CURDATE() AND payment_type='CREDIT' AND status='SUCCESS'", nativeQuery = true)
	public int SumForTodayByCredit();

	// For All Transaction
	@Query(value="select sum(total_amount) from merchant_transaction_details where status='SUCCESS'", nativeQuery = true)
	public int TotalAmount();

	@Query(value="select sum(total_amount) from merchant_transaction_details where payment_type='DEBIT' AND status='SUCCESS'", nativeQuery = true)
	public int TotalAmountByDebit();

	@Query(value="select sum(total_amount) from merchant_transaction_details where payment_type='CREDIT'AND status='SUCCESS'", nativeQuery = true)
	public int TotalAmountByCredit();

	@Query(value="select sum(total_amount) from merchant_transaction_details where date like ?1%", nativeQuery = true)
	public int AmountByDate(String date);

	// For last one year's transaction
	@Query(value = "SELECT SUM(total_amount) FROM merchant_transaction_details t where DATE(t.date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 YEAR ) AND CURDATE() AND status='SUCCESS'", nativeQuery = true)
	public int SumInLastYear();

	@Query(value = "SELECT SUM(total_amount) FROM merchant_transaction_details t where DATE(t.date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 YEAR ) AND CURDATE() AND payment_type='CREDIT' AND status='SUCCESS'", nativeQuery = true)
	public int SumInLastYearByCredit();

	@Query(value = "SELECT SUM(total_amount) FROM merchant_transaction_details t where DATE(t.date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 YEAR ) AND CURDATE() AND payment_type='DEBIT' AND status='SUCCESS'", nativeQuery = true)
	public int SumInLastYearByDebit();

	// For One month's transaction
	@Query(value = "SELECT SUM(total_amount) FROM merchant_transaction_details t where DATE(t.date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 MONTH ) AND CURDATE() AND payment_type='CREDIT' AND status='SUCCESS'", nativeQuery = true)
	public int SumInLastMonthByCredit();

	@Query(value = "SELECT SUM(total_amount) FROM merchant_transaction_details t where DATE(t.date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 MONTH ) AND CURDATE() AND payment_type='DEBIT'  AND status='SUCCESS'", nativeQuery = true)
	public int SumInLastMonthByDebit();

	@Query(value = "SELECT SUM(total_amount) FROM merchant_transaction_details t where DATE(t.date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 MONTH ) AND CURDATE()  AND status='SUCCESS'", nativeQuery = true)
	public int SumInLastMonth();

	// For One week transaction
	@Query(value = "SELECT SUM(total_amount) FROM merchant_transaction_details t where DATE(t.date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 WEEK ) AND CURDATE() AND status='SUCCESS'", nativeQuery = true)
	public int SumInLastWeek();

	@Query(value = "SELECT SUM(total_amount) FROM merchant_transaction_details t where DATE(t.date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 WEEK ) AND CURDATE() AND payment_type='DEBIT' AND status='SUCCESS'", nativeQuery = true)
	public int SumInLastWeekByDebit();

	@Query(value = "SELECT SUM(total_amount) FROM merchant_transaction_details t where DATE(t.date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 WEEK ) AND CURDATE() AND payment_type='CREDIT' AND status='SUCCESS'", nativeQuery = true)
	public int SumInLastWeekByCredit();

	// For percentage Data
	@Query(value="Select COUNT(*) from merchant_transaction_details WHERE payment_type='CREDIT' AND status='SUCCESS'", nativeQuery = true)
	public int SuccessfulCreditPayment();

	@Query(value="Select COUNT(*) from merchant_transaction_details WHERE payment_type='DEBIT' AND status='SUCCESS'", nativeQuery = true)
	public int SuccessfulDebitPayment();

	@Query(value="Select COUNT(*) from merchant_transaction_details", nativeQuery = true)
	public int TotalTransaction();

	@Query(value="Select COUNT(*) from merchant_transaction_details WHERE status='SUCCESS'", nativeQuery = true)
	public int SuccessfuPayment();

	// Data For Graph in Week
	@Query(value = "select distinct date from merchant_transaction_details where DATE(date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 WEEK ) AND CURDATE()", nativeQuery = true)
	public List<String> GraphDataDateWeek();

	@Query(value = "select COUNT(*) as TOTALTRIP from merchant_transaction_details  where DATE(date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 WEEK ) AND CURDATE() group by date ", nativeQuery = true)
	public List<Integer> GraphDataDateCountWeek();

	// @Query(value="select date ,COUNT(*) as transaction from transaction_details
	// where DATE(date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 WEEK ) AND
	// CURDATE() AND status='SUCCESS' group by date",nativeQuery = true)
	// public List<Object> Demo();

	// Data for Graph in Month
	@Query(value = "select distinct date from merchant_transaction_details where DATE(date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 MONTH ) AND CURDATE()", nativeQuery = true)
	public List<String> GraphDataDateMonth();

	@Query(value = "select COUNT(*) as TOTALTRIP from merchant_transaction_details  where DATE(date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 Month ) AND CURDATE() group by date", nativeQuery = true)
	public List<Integer> GraphDataDateCountMonth();

	// Data for Graph in Year
	@Query(value = "select distinct date from merchant_transaction_details where DATE(date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 YEAR ) AND CURDATE()", nativeQuery = true)
	public List<String> GraphDataDateYear();

	@Query(value = "select COUNT(*) as TOTALTRIP from merchant_transaction_details  where DATE(date) BETWEEN DATE_SUB( CURDATE( ) ,INTERVAL 1 YEAR ) AND CURDATE() group by date", nativeQuery = true)
	public List<Integer> GraphDataDateCountYear();

	// SELECT SUM(total_amount) FROM transaction_details where DATE(date) =
	// CURDATE();

}
