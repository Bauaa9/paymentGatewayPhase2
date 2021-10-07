package com.slytherin.project.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.slytherin.project.dao.MerchantDao;
import com.slytherin.project.dao.UserDao;
import com.slytherin.project.model.DAOMerchant;
import com.slytherin.project.model.DAOUser;
import com.slytherin.project.model.UserDTO;

//replace service with merchant service by shubham and tejas
@Service
public class JwtMerchantDetailsService implements  UserDetailsService{

	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DAOMerchant user = merchantDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
	
	public DAOMerchant  save(UserDTO user) {
		DAOMerchant  newUser = new DAOMerchant ();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return merchantDao.save(newUser);
	}
}
