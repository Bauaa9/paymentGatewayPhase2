package com.slytherin.project.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.slytherin.project.dao.AddressRepository;
import com.slytherin.project.dao.CarddetailsRepo;
import com.slytherin.project.dao.CardlimitRepo;
import com.slytherin.project.dao.CustomerRepo;
import com.slytherin.project.dao.ProfileRepo;
import com.slytherin.project.dao.TransactionDao;
import com.slytherin.project.model.CustomerProfileData;
import com.slytherin.project.model.DAOUser;
import com.slytherin.project.model.ModelAddress;
import com.slytherin.project.model.ModelCardlimit;
import com.slytherin.project.model.ModelInputCardDetails;
import com.slytherin.project.model.ModelSavedCardDetails;
import com.slytherin.project.model.ModelTransaction;

@Service

public class CustomerService {

	@Autowired
	CustomerRepo repoCustomer;

	@Autowired
	CarddetailsRepo repoCardDetails;

	@Autowired
	CardlimitRepo repoCardLimitDetails;

	@Autowired
	ProfileRepo repoProfile;

	@Autowired
	TransactionDao transactionDao;

	@Autowired
	AddressRepository addressRepo;

	@Autowired
	private Environment env;
	

	public Map<String, Object> getCreditDetailsFromBank() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		ModelSavedCardDetails obj = repoCardDetails.findCard(getUserId());
		ModelInputCardDetails ModelInputCardDetails = new ModelInputCardDetails(obj.getCard_id(), getUserId());

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ModelInputCardDetails> request = new HttpEntity<ModelInputCardDetails>(ModelInputCardDetails,
				header);
		Map<String, Object> map = restTemplate.postForObject(env.getProperty("bankServerGetCardDetailsUrl").toString(),
				request, Map.class);
		return map;

	}

	public Map<String, Object> getBilledTxnFromBank() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		ModelSavedCardDetails obj = repoCardDetails.findCard(getUserId());
		ModelInputCardDetails ModelInputCardDetails = new ModelInputCardDetails(obj.getCard_id(), getUserId());

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ModelInputCardDetails> request = new HttpEntity<ModelInputCardDetails>(ModelInputCardDetails,
				header);
		Map<String, Object> map = restTemplate.postForObject(env.getProperty("bankServerGetBilledTxnUrl").toString(),
				request, Map.class);
		return map;

	}

	public Map<String, Object> getUnBilledTxnFromBank() throws Exception{

		RestTemplate restTemplate = new RestTemplate();
		ModelSavedCardDetails obj = repoCardDetails.findCard(getUserId());
		ModelInputCardDetails ModelInputCardDetails = new ModelInputCardDetails(obj.getCard_id(), getUserId());

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ModelInputCardDetails> request = new HttpEntity<ModelInputCardDetails>(ModelInputCardDetails,
				header);
		Map<String, Object> map = restTemplate.postForObject(env.getProperty("bankServerGetUnBilledTxnUrl").toString(),
				request, Map.class);
		return map;

	}

	public Map<String, Object> getRetailTxnFromBank() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		ModelSavedCardDetails obj = repoCardDetails.findCard(getUserId());
		ModelInputCardDetails ModelInputCardDetails = new ModelInputCardDetails(obj.getCard_id(), getUserId());

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ModelInputCardDetails> request = new HttpEntity<ModelInputCardDetails>(ModelInputCardDetails,
				header);
		Map<String, Object> map = restTemplate.postForObject(env.getProperty("bankServerGetRetailTxnUrl").toString(),
				request, Map.class);
		return map;

	}

//	public Map<String, Object> creditcarddetails() throws Exception {
//
//		ModelSavedCardDetails obj = repoCardDetails.findCard(getUserId());
//		ModelCardlimit obj1 = repoCardLimitDetails.findLimit(obj.getCard_id());
////		UtilAES utilAES = new UtilAES();
//		obj.setCard_number(utilAES.decrypt(obj.getCard_number()));
//		Map<String, Object> map = new HashMap<String, Object>();
//		double totaloutstanding = Double.valueOf(obj1.getTotalcreditlimit())
//				- Double.valueOf(obj1.getAvailablecreditlimit());
//		map.put("cardetails", obj);
//		map.put("cardlimit", obj1);
//		map.put("totaloutstanding", totaloutstanding);
//		return map;
//
//	}

	public Map<String, Object> getUnbilledTxn() throws Exception{

		ModelSavedCardDetails obj = repoCardDetails.findCard(getUserId());
		ModelCardlimit obj1 = repoCardLimitDetails.findLimit(obj.getCard_id());
		String nextStatementDate = transactionDao.findNextStmtDate(obj1.getLaststatementdate());
		System.out.println(nextStatementDate);
		List<ModelTransaction> modelTransaction = transactionDao.findUnBilledTransactions(obj1.getLaststatementdate(),
				nextStatementDate);
		Map<String, Object> map = new HashMap<String, Object>();
		Float totalOutstandingAmount = transactionDao.getTotalOutstandingAmount(obj1.getLaststatementdate(),
				nextStatementDate);
		System.out.println(totalOutstandingAmount);
		map.put("unbilledTxn", modelTransaction);
		map.put("totalOutstandingAmount", totalOutstandingAmount);
		return map;

	}

	public Map<String, Object> getBilledTxn() throws Exception{

		ModelSavedCardDetails obj = repoCardDetails.findCard(getUserId());
		ModelCardlimit obj1 = repoCardLimitDetails.findLimit(obj.getCard_id());
		System.out.println(obj1.getLaststatementdate());
		String previousStatementDate = transactionDao.findPreviousStmtDate(obj1.getLaststatementdate());
		List<ModelTransaction> modelTransaction = transactionDao.findBilledTransactions(obj1.getLaststatementdate(),
				previousStatementDate);
		Map<String, Object> map = new HashMap<String, Object>();
		Float totalAmountDue = transactionDao.getTotalAmountDue(obj1.getLaststatementdate(), previousStatementDate);
		map.put("billedTxn", modelTransaction);
		map.put("totalAmountDue", totalAmountDue);
		map.put("minAmountDue", (int) (totalAmountDue / 9));
		return map;

	}

	public int getUserId() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		DAOUser modelCustomerLogin = repoCustomer.findUser(username);
		return (int) modelCustomerLogin.getId();
	}

	public ModelSavedCardDetails addCard(ModelSavedCardDetails cd) throws Exception {
		
		cd.setCustomer_id(getUserId());
		cd.setCard_id((int) repoCardDetails.count() + 1);
		UtilAES utilAES = new UtilAES();
		cd.setCard_number(utilAES.encrypt(cd.getCard_number()));
		cd.setCard_holder_name(utilAES.encrypt(cd.getCard_holder_name()));
//		cd.setCard_type(utilAES.encrypt(cd.getCard_type()));
		cd.setExpiry_date(utilAES.encrypt(cd.getExpiry_date()));
		cd.setCvv(utilAES.encrypt(cd.getCvv()));
		System.out.println(cd.getCard_number());
//		System.out.println(decrypt(cd.getCard_number(), secretkey));

		return (ModelSavedCardDetails) repoCardDetails.save(cd);

	}

	public Map<String, Object> getRetailTxn() throws Exception {
		Map<String, Object> map;

		ModelSavedCardDetails obj = repoCardDetails.findCard(getUserId());
		List<ModelTransaction> modelTransaction = transactionDao.findRetailTransactions();
		map = new HashMap<String, Object>();
		map.put("retailTxn", modelTransaction);
		return map;

	}

	public Map<String, Object> displayAll() throws Exception {
		UtilAES utilAES = new UtilAES();
		try {
			List<ModelSavedCardDetails> temp = repoCardDetails.findAll();

			for (int i = 0; i < temp.size(); i++) {
				ModelSavedCardDetails modelCard = temp.get(i);
				modelCard.setCard_number(utilAES.decrypt(modelCard.getCard_number()));
				modelCard.setCard_holder_name(utilAES.decrypt(modelCard.getCard_holder_name()));
//				modelCard.setCard_type(utilAES.decrypt(modelCard.getCard_type()));
				modelCard.setCvv(utilAES.decrypt(modelCard.getCvv()));
				modelCard.setExpiry_date(utilAES.decrypt(modelCard.getExpiry_date()));
				System.out.println("sdfsdf23423");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("allCards", temp);
			return map;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public void deleteCard(Integer cardId) {
		repoCardDetails.deleteById(cardId);
	}

	public Map<String, Object> getProfileById() throws Exception {
		CustomerProfileData profile = repoProfile.getUserDetailsById(getUserId());
		Map<String, Object> map = new HashMap<>();
		map.put("profileDetails", profile);
		return map;
	}

	public Map<String, Object> updateProfileById(CustomerProfileData profileDetails) throws Exception{

		CustomerProfileData profile = repoProfile.getUserDetailsById(getUserId());
		profile.setFirstName(profileDetails.getFirstName());
		profile.setLastName(profileDetails.getLastName());
		profile.setEmail(profileDetails.getEmail());
		profile.setPhoneNo(profileDetails.getPhoneNo());
		CustomerProfileData updatedProfile = repoProfile.save(profile);
		Map<String, Object> map = new HashMap<>();
		map.put("profileDetails", updatedProfile);
		return map;

	}

	public Map<String, Object> getAllAddress() throws Exception{
		List<ModelAddress> listAddresses = addressRepo.findAllAddresses(getUserId());
		Map<String, Object> map = new HashMap<>();
		map.put("addresses", listAddresses);
		return map;
	}

	public Map<String, Object> addAddress(ModelAddress address) throws Exception{
		address.setUser_id(getUserId());
		System.out.println(address.toString());
		addressRepo.save(address);
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Added address succesfully");
		return map;

	}

	public Map<String, Object> deleteAddress(@PathVariable int id) throws Exception{

		ModelAddress address = addressRepo.findById(id).orElseThrow();
		addressRepo.delete(address);
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Deleted address succesfully");
		return map;

	}

}