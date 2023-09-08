package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Agent;

public interface AgentRepo extends JpaRepository<Agent, Integer>{
	public Agent findByUserUsername(String username);
}
