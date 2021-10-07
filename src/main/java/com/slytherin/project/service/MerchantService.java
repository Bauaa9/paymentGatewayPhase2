package com.slytherin.project.service;

import java.sql.Timestamp;
import java.util.Date;
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
		boolean verificationStatus = true;
		verificationStatus = verifyMerchant(newXaction.getMerchentId(), newXaction.getSecretKey());
		
		if (verificationStatus == true) {
			
			UUID uuid = UUID.randomUUID();
	        String uuidAsString = uuid.toString();
	        
			PgRefNum tokenId = new PgRefNum();
			tokenId.setPgRefId(uuidAsString);
			
			LOG.info("Merchant Verification sucessfull");
			
			MerchantTransaction merchantTransaction = new MerchantTransaction();
			merchantTransaction.setPgRefId(uuidAsString);
			merchantTransaction.setDataTime(getTimeStamp());
			merchantTransaction.setOrderId(newXaction.getOrderId());
			merchantTransaction.setStatus("Pending");
			
			merchantTxnRepo.save(merchantTransaction);
			
			return new ResponseEntity<PgRefNum>(tokenId, HttpStatus.OK);

		} else {
			LOG.info("Merchant Verification failed");
			return null;
		}

	}

	public boolean verifyMerchant(String merchantId, String secretKey) throws Exception {
		
		MerchantData mer = merchantDataRepo.findById(merchantId).get();
		
		if (merchantId.equals(mer.getMerchantId()) && secretKey.equals(mer.getSecretKey())) {
			return true;
		} else {
			return false;
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
}
