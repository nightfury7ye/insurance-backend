package com.techlabs.insurance.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Customer;
import java.util.List;


public interface CustomerRepo extends JpaRepository<Customer, Integer>{

  Page<Customer> findByUserStatusStatusid(int statusid, Pageable pageable);
  
  Page<Customer> findByDocumentStatus(String documentStatus, Pageable pageable);

  Customer findByUserUsername(String username);
  
  Page<Customer> findByAgentAgentid(int agent_agentid, Pageable pageable);
}
