package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.techlabs.insurance.entities.Agent;

public interface AgentService {
	public ResponseEntity<Agent> addAgent(Agent agent, int statusId);
	public void deleteAgent(int agentId);
	public ResponseEntity<Agent> updateAgentStatus(int agentId, int statusId);
	public ResponseEntity<Page<Agent>> getAllAgents(int page, int size);
	
	public Agent registerAgent(Agent agent);
	public Agent getAgentByUsername(String username);
	public ResponseEntity<Agent> updateAgentProfile(String username, Agent updatedAgent);
	
	public void activeAgentStatus(int agentId);
	public void inactiveAgentStatus(int agentId);
}
