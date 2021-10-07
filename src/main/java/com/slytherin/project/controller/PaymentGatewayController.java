package com.slytherin.project.controller;

import java.util.HashMap;

/**
 * @author Tejas Abhang
 */

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.slytherin.project.model.FinalResult;
import com.slytherin.project.model.OtpData;
import com.slytherin.project.model.PaymentDetails;
import com.slytherin.project.model.PgRefNum;
import com.slytherin.project.model.Transaction;
import com.slytherin.project.service.MerchantService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentGatewayController {

	private static Logger LOG = LoggerFactory.getLogger(PaymentGatewayController.class);

	@Autowired
	MerchantService merchantService;

	@PostMapping(path = "/generate-id", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> generateId(@RequestBody Transaction newXaction) {

//		ResponseEntity<PgRefNum> result = new ResponseEntity<PgRefNum>(null);
		try {

			ResponseEntity<PgRefNum> result= merchantService.verifyAndGetId(newXaction);
			System.out.println(result);
			LOG.info("Transaction created");
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}

	}

	@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Authorization")
	@PostMapping(value = "/process-payment")
	public Map<String, String> transferinfo(@RequestBody PaymentDetails paymentDetails)  {
		
		try {
			Map<String, String> response = new HashMap<String, String>();
			response = merchantService.forwardDataToBank(paymentDetails);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
		
	}

	@PostMapping("/check-status/{pgRefId}")
	public ResponseEntity<String> getStatus(@PathVariable("pgRefId") String pgRefId) {

		try {
			String status = merchantService.getStatus(pgRefId);
			return ResponseEntity.status(HttpStatus.OK).body(status);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<FinalResult> verifyOtp(@RequestBody OtpData otpData) {

		try {
			FinalResult response = merchantService.verifyOtp(otpData);

			merchantService.updateTransaction(response);

			return new ResponseEntity<FinalResult>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}

	}

}
