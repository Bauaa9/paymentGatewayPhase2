package com.slytherin.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.slytherin.project.model.CustomerProfileData;
import com.slytherin.project.model.ModelAddress;
import com.slytherin.project.model.ModelSavedCardDetails;
import com.slytherin.project.service.CustomerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	@Autowired
	CustomerService service;

	@PostMapping("/creditdetails")
	public ResponseEntity<?> cardinfo() {
		try {
			Map<String, Object> map = service.getCreditDetailsFromBank();
			
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}

	@PostMapping("/unbilled-transactions")
	public ResponseEntity<?> getUnbilledTrans() {
		try {
			Map<String, Object> map = service.getUnBilledTxnFromBank();
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}

	@PostMapping("/billed-transactions")
	public ResponseEntity<?> getBilledTrans() {
		try {
			Map<String, Object> map = service.getBilledTxnFromBank();
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}

	@PostMapping("/retail-transactions")
	public ResponseEntity<?> getRetailTransactions() {
		try {
			Map<String, Object> map = service.getRetailTxnFromBank();
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}
	
	@PostMapping(value = "/new-card")
	public ResponseEntity<?> addCard(@RequestBody ModelSavedCardDetails newCard) {
		try {
			return ResponseEntity.ok(service.addCard(newCard));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}

	@PostMapping(value = "/display-cards")
	public ResponseEntity<?> displayAll() {
		try {
			Map<String, Object> map = service.displayAll();
			return ResponseEntity.ok(map);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}

	@DeleteMapping(value = "/delete-card/{id}")
	public ResponseEntity<?> deleteCard(@PathVariable("id") int cardId) {
		System.out.println(cardId);
		try {
			service.deleteCard(cardId);
			return ResponseEntity.ok("Card deleted successfully!!!");
		} catch (Exception e) {
			System.err.println("## EXCEPTION: " + e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/get-profile")
	public ResponseEntity<?> getProfileById() {
		try {
			return ResponseEntity.ok(service.getProfileById());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}

	@PostMapping("/update-profile")
	public ResponseEntity<?> updateProfile(@RequestBody CustomerProfileData profile) {
		try {
			return ResponseEntity.ok(service.updateProfileById(profile));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}
	
	@PostMapping(value = "/add-address")
	public ResponseEntity<?> addAddress(@RequestBody ModelAddress modelAddress) {
		try {
			return ResponseEntity.ok(service.addAddress(modelAddress));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}
	
	@PostMapping("/addresses")
	public ResponseEntity<?>  getAllAddress()
	{
		try {
			return ResponseEntity.ok(service.getAllAddress());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}
	
	@DeleteMapping("/delete/address/{id}")
	public ResponseEntity<?> deleteAddress(@PathVariable int id)
	{
		try {
			return ResponseEntity.ok(service.deleteAddress(id));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something Went wrong. Try again later", e);
		}
	}
	

}