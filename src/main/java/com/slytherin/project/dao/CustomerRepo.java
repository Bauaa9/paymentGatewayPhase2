package com.slytherin.project.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.slytherin.project.model.DAOUser;


@Repository
public interface CustomerRepo extends JpaRepository<DAOUser,Integer> {
	
	@Query(value="select * from customer_login where username=?1", nativeQuery=true)
	public DAOUser findUser(String username);

}
