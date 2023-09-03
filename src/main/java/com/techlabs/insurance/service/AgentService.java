package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.techlabs.insurance.entities.Agent;

public interface AgentService {
	public Agent addAgent(Agent agent, int statusId);
	public void deleteAgent(int agentId);
	public Agent updateAgentStatus(int agentId, int statusId);
	public ResponseEntity<Page<Agent>> getAllAgents(int page, int size);
	
	public Agent registerAgent(Agent agent);
	public Agent getAgentByUsername(String username);
	public Agent updateAgentProfile(String username, Agent updatedAgent);
}
