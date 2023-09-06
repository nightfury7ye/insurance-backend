package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Commision;
import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Policy;
import com.techlabs.insurance.payload.CommissionDto;

public interface CommisionService {
	Commision saveCommision(double amount, Agent agent, Customer customer, Policy policy);
	
	public Page<CommissionDto> getCommisionsForAgent(int agentid, int page, int pageSize);
	
	public Page<CommissionDto> getCommisions(int page, int pageSize);
	
	public void approveCommission(int commissionid);
	
	void withdrawCommission(int commissionid);
}
