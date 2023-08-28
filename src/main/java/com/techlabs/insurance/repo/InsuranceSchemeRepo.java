package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.InsuranceScheme;

public interface InsuranceSchemeRepo extends JpaRepository<InsuranceScheme, Integer>{
	
}
