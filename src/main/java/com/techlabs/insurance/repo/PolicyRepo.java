package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Policy;

public interface PolicyRepo extends JpaRepository<Policy, Integer>{   
	
}
  