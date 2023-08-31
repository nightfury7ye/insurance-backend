package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Employee;
import com.techlabs.insurance.entities.User_status;

public interface AgentService {
	public Agent addAgent(Agent agent);
	public void deleteAgent(int agentId);
	public Agent updateAgentStatus(int agentId, User_status userStatus);
	Page<Agent> getAllAgents(int page, int size);
}
