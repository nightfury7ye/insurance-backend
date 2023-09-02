package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	Customer findByUserUsername(String username);
}
