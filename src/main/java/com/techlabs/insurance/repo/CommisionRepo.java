package com.techlabs.insurance.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Commision;


public interface CommisionRepo extends JpaRepository<Commision, Integer>{
	Page<Commision> findByAgent(Agent agent, Pageable pageable);
}
