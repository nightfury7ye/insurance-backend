package com.techlabs.insurance.repo;

<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
=======
>>>>>>> 7f03db4f67c5e270770be44df1df193d4aa305c6
import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{
<<<<<<< HEAD
  Page<Customer> findByUserstatusStatusid(int statusid, Pageable pageable);
=======
<<<<<<< HEAD

=======
  Page<Customer> findByStatusid(int statusid, Pageable pageable);
>>>>>>> 7f03db4f67c5e270770be44df1df193d4aa305c6
	Customer findByUserUsername(String username);
>>>>>>> b26263f39227b2e02c60fbb0b75e423a02eb20cb
}
