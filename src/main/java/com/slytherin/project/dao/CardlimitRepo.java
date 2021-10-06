package com.slytherin.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.slytherin.project.model.ModelCardlimit;


@Repository
public interface CardlimitRepo extends JpaRepository<ModelCardlimit, Integer> {
	
	@Query(value="select * from cardlimitdetails where card_id=?1", nativeQuery=true)
	public ModelCardlimit findLimit(int id);

}
