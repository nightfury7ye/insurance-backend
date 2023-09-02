package com.techlabs.insurance.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{
  Page<Customer> findByUserstatusStatusid(int statusid, Pageable pageable);
	Customer findByUserUsername(String username);
}
