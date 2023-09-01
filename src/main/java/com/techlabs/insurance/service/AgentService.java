package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entities.Agent;

public interface AgentService {
	public Agent addAgent(Agent agent, int statusId);
	public void deleteAgent(int agentId);
	public Agent updateAgentStatus(int agentId, int statusId);
	Page<Agent> getAllAgents(int page, int size);
}
