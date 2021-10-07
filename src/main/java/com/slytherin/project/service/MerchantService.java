package com.slytherin.project.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Tejas Abhang
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.slytherin.project.controller.PaymentGatewayController;
import com.slytherin.project.dao.MerchantDataRepository;
import com.slytherin.project.dao.MerchantTransactionRepository;
import com.slytherin.project.model.FinalResult;
import com.slytherin.project.model.MerchantData;
import com.slytherin.project.model.MerchantTransaction;
import com.slytherin.project.model.Merchantdashboard;
import com.slytherin.project.model.MerchantlastWeekData;
import com.slytherin.project.model.OtpData;
import com.slytherin.project.model.PaymentDetails;
import com.slytherin.project.model.PgRefNum;
import com.slytherin.project.model.Transaction;


@Service
public class MerchantService {

	private static Logger LOG = LoggerFactory.getLogger(PaymentGatewayController.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	MerchantDataRepository merchantDataRepo;
	
	@Autowired
	MerchantTransactionRepository merchantTxnRepo;

	public ResponseEntity<PgRefNum> verifyAndGetId(Transaction newXaction) throws Exception{
		String verificationStatus = null;
		verificationStatus = verifyMerchant(newXaction.getMerchentId(), newXaction.getSecretKey());
		
		if (verificationStatus != null) {
			
			UUID uuid = UUID.randomUUID();
	        String uuidAsString = uuid.toString();
	        
			PgRefNum tokenId = new PgRefNum();
			tokenId.setPgRefId(uuidAsString);
			tokenId.setMerchantName(verificationStatus);
			LOG.info("Merchant Verification sucessfull");
			
			MerchantTransaction merchantTransaction = new MerchantTransaction();
			merchantTransaction.setPgRefId(uuidAsString);
			merchantTransaction.setDataTime(getTimeStamp());
			merchantTransaction.setOrderId(newXaction.getOrderId());
			merchantTransaction.setStatus("Pending");
			
			merchantTxnRepo.save(merchantTransaction);
			System.out.println(tokenId.getPgRefId());
			return new ResponseEntity<PgRefNum>(tokenId, HttpStatus.OK);

		} else {
			LOG.info("Merchant Verification failed");
			return null;
		}

	}

	public String verifyMerchant(String merchantId, String secretKey) throws Exception {
		
		MerchantData mer = merchantDataRepo.findById(merchantId).get();
		
		if (merchantId.equals(mer.getMerchantId()) && secretKey.equals(mer.getSecretKey())) {
			return mer.getMerchantName();
		} else {
			return null;
		}
	}
	
	public String getTimeStamp() 
	{
		Date date = new Date();
		long time = date.getTime();
		String result = String.valueOf(new Timestamp(time));
		return result;
	}

	public String getStatus(String pgRefId) throws Exception{
		MerchantTransaction finalStatus = merchantTxnRepo.findById(pgRefId).get();
		return finalStatus.getStatus();
	}

	public FinalResult verifyOtp(OtpData otpData) throws Exception{
		String url = env.getProperty("validateOtpUrl").toString();
		LOG.info("OTP data received");
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<OtpData> request = new HttpEntity<>(otpData, header);
		System.out.println(otpData.toString());
		
		RestTemplate restTemplate = new RestTemplate();
		FinalResult response = restTemplate.postForEntity(url, request, FinalResult.class).getBody();
		System.out.println(response);
		return response;
	}

	public void updateTransaction(FinalResult response) throws Exception {
		MerchantTransaction finalUpdate = new MerchantTransaction();
		finalUpdate = merchantTxnRepo.findById(response.getPgRefId()).get();
		finalUpdate.setPgRefId(response.getPgRefId());
		finalUpdate.setStatus(response.getStatus());
		merchantTxnRepo.save(finalUpdate);
		
	}

	public Map<String, String> forwardDataToBank(PaymentDetails paymentDetails) throws Exception{
		System.out.println(paymentDetails.toString());
		LOG.info("Received Card Data");
		
		String url = env.getProperty("validateCardUrl").toString();
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate template = new RestTemplate();
		HttpEntity<PaymentDetails> request = new HttpEntity<PaymentDetails>(paymentDetails, header);
		Map<String, String> response = template.postForObject(url, request, Map.class);
		System.out.println(response);
		return response;
	}
	
	public List<MerchantTransaction> getAllData() {
		List<MerchantTransaction> allData = new ArrayList<MerchantTransaction>();
		try {
			merchantTxnRepo.findAll().forEach(c1 -> allData.add(c1));
			return allData;
		} catch (Exception e) {
			return allData;
		}

	}

	public List<Merchantdashboard> forFilter(String filter) {
		List<Merchantdashboard> filteredData = new ArrayList<Merchantdashboard>();
		Merchantdashboard d = new Merchantdashboard();
		if (filter.equals("Year")) {

			try {
				int all = merchantTxnRepo.SumInLastYear();
				d.setAll(all);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setAll(0);
			}

			try {
				int allBycredit = merchantTxnRepo.SumInLastYearByCredit();
				d.setByCredit(allBycredit);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setByCredit(0);
			}

			try {

				int allBydebit = merchantTxnRepo.SumInLastYearByDebit();
				d.setByDebit(allBydebit);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setByDebit(0);
			}

			try {

				int today = merchantTxnRepo.SumForToday();
				d.setForToday(today);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setForToday(0);
			}

			filteredData.add(d);
			return filteredData;

		}

		else if (filter.equals("Month")) {

			try {
				int all = merchantTxnRepo.SumInLastMonth();
				d.setAll(all);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setAll(0);
			}

			try {
				int allBycredit = merchantTxnRepo.SumInLastMonthByCredit();
				d.setByCredit(allBycredit);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setByCredit(0);
			}

			try {
				int allBydebit = merchantTxnRepo.SumInLastMonthByDebit();

				d.setByDebit(allBydebit);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;

				d.setByDebit(0);
			}

			try {
				int today = merchantTxnRepo.SumForToday();

				d.setForToday(today);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;

				d.setForToday(0);
			}

			filteredData.add(d);
			return filteredData;

		}

		else if (filter.equals("Today")) {

			try {
				int all = merchantTxnRepo.SumForToday();
				d.setAll(all);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setAll(0);
			}

			try {
				int allBycredit = merchantTxnRepo.SumForTodayByCredit();
				d.setByCredit(allBycredit);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setByCredit(0);
			}

			try {
				int allBydebit = merchantTxnRepo.SumForTodayByDebit();
				d.setByDebit(allBydebit);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setByDebit(0);
			}

			try {
				int today = merchantTxnRepo.SumForToday();
				d.setForToday(today);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setForToday(0);
			}

			System.out.println(d);

			filteredData.add(d);
			return filteredData;
		}

		else {

			try {
				int all = merchantTxnRepo.SumInLastWeek();
				d.setAll(all);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setAll(0);
			}

			try {
				int allBycredit = merchantTxnRepo.SumInLastWeekByCredit();
				d.setByCredit(allBycredit);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setByCredit(0);
			}

			try {
				int today = merchantTxnRepo.SumForToday();
				d.setForToday(today);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setForToday(0);
			}

			try {
				int allBydebit = merchantTxnRepo.SumInLastWeekByDebit();
				d.setByDebit(allBydebit);
			} catch (Exception e) {
				// e.printStackTrace();
				// int all=0;
				d.setByDebit(0);
			}

			filteredData.add(d);
			return filteredData;
		}
	}

	public List<Merchantdashboard> forFirstRow() {
		List<Merchantdashboard> firstRow = new ArrayList<Merchantdashboard>();
		Merchantdashboard d1 = new Merchantdashboard();

		try {
			int allTransaction = merchantTxnRepo.TotalAmount();
			d1.setAll(allTransaction);
		} catch (Exception e) {
			// e.printStackTrace();
			// int all=0;
			d1.setAll(0);
		}

		try {
			int allTransactionBycredit = merchantTxnRepo.TotalAmountByCredit();
			d1.setByCredit(allTransactionBycredit);
		} catch (Exception e) {
			// e.printStackTrace();
			// int all=0;
			d1.setByCredit(0);
		}

		try {
			int allTransactionBydebit = merchantTxnRepo.TotalAmountByDebit();
			d1.setByDebit(allTransactionBydebit);
		} catch (Exception e) {
			// e.printStackTrace();
			// int all=0;

			d1.setByDebit(0);
		}

		try {
			int allTransactionToday = merchantTxnRepo.SumForToday();
			d1.setForToday(allTransactionToday);
		} catch (Exception e) {
			// e.printStackTrace();
			// int all=0;

			d1.setForToday(0);
		}

		firstRow.add(d1);
		return firstRow;
	}

	public int percentage(double a, double b) {
		double c = a / b;
		double d = c * 100;
		int e = (int) d;
		return e;
	}

	public List<Integer> percentage() {
		List<Integer> per = new ArrayList<Integer>();

		double allTransaction = merchantTxnRepo.TotalTransaction();
		double successfulTransaction = merchantTxnRepo.SuccessfuPayment();
		double successfulCreditTransaction = merchantTxnRepo.SuccessfulCreditPayment();
		double successfulDebitTransaction = merchantTxnRepo.SuccessfulDebitPayment();
		double failure = allTransaction - successfulTransaction;

		int perCredit = percentage(successfulCreditTransaction, successfulTransaction);
		int perDebit = percentage(successfulDebitTransaction, successfulTransaction);
		int perSucc = percentage(successfulTransaction, allTransaction);
		int perFailure = percentage(failure, allTransaction);

		per.add(0, perCredit);
		per.add(1, perDebit);
		per.add(2, perSucc);
		per.add(3, perFailure);

		return per;

	}

	public List<MerchantlastWeekData> lastWeek(String x) {
		List<MerchantlastWeekData> allData = new ArrayList<MerchantlastWeekData>();

		if (x.equals("Week")) {

			List<String> date = new ArrayList<String>();
			List<Integer> count = new ArrayList<Integer>();
			merchantTxnRepo.GraphDataDateWeek().forEach(c1 -> date.add(c1));

			merchantTxnRepo.GraphDataDateCountWeek().forEach(c2 -> count.add(c2));
			// System.out.println(count);

			for (int i = 0; i < date.size(); i++) {
				MerchantlastWeekData g = new MerchantlastWeekData();
				g.setDate(date.get(i));
				g.setTransaction(count.get(i));
				allData.add(g);

			}
			System.out.println("Under Week");
			return allData;

		}

		else if (x.equals("Month")) {

			List<String> date = new ArrayList<String>();
			List<Integer> count = new ArrayList<Integer>();
			merchantTxnRepo.GraphDataDateMonth().forEach(c1 -> date.add(c1));

			merchantTxnRepo.GraphDataDateCountMonth().forEach(c2 -> count.add(c2));
			// System.out.println(count);

			for (int i = 0; i < date.size(); i++) {
				MerchantlastWeekData g = new MerchantlastWeekData();
				g.setDate(date.get(i));
				g.setTransaction(count.get(i));
				allData.add(g);

			}
			System.out.println("Under Month");
			return allData;
		}

		else {

			System.out.println("Else me hu");

			List<String> date = new ArrayList<String>();
			List<Integer> count = new ArrayList<Integer>();

			merchantTxnRepo.GraphDataDateYear().forEach(c1 -> date.add(c1));

			merchantTxnRepo.GraphDataDateCountYear().forEach(c2 -> count.add(c2));
			// System.out.println(count);

			for (int i = 0; i < date.size(); i++) {
				MerchantlastWeekData g = new MerchantlastWeekData();
				g.setDate(date.get(i));
				g.setTransaction(count.get(i));
				allData.add(g);
				System.out.println(date.get(i));
				System.out.println(count.get(i));

			}

			return allData;

		}

	}
}
