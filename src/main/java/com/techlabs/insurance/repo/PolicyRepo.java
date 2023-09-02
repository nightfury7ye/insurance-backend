package com.techlabs.insurance.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Policy;

public interface PolicyRepo extends JpaRepository<Policy, Integer>{   
	Page<Policy> findByCustomerCustomerid(int customerid, Pageable pageable);
}
  