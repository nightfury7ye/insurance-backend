package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Commision;

public interface CommisionRepo extends JpaRepository<Commision, Integer>{
	
}
