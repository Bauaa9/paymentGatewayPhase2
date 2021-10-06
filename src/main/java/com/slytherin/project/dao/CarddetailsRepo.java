package com.slytherin.project.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.slytherin.project.model.ModelSavedCardDetails;


@Repository
public interface CarddetailsRepo extends JpaRepository<ModelSavedCardDetails, Integer> {
	
	@Query(value="select * from customer_saved_cards where customer_id=?1 and card_type='credit' limit 1", nativeQuery=true)
	public ModelSavedCardDetails findCard(int id);
	
	@Query(value="select * from customer_saved_cards where customer_id=?1", nativeQuery=true)
	public List<ModelSavedCardDetails> findByUserId(int id);

}
