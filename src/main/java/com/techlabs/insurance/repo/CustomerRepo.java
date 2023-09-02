package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{
<<<<<<< HEAD

=======
  Page<Customer> findByStatusid(int statusid, Pageable pageable);
	Customer findByUserUsername(String username);
>>>>>>> b26263f39227b2e02c60fbb0b75e423a02eb20cb
}
