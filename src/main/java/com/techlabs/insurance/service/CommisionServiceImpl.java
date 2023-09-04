package com.techlabs.insurance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Commision;
import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Policy;
import com.techlabs.insurance.repo.CommisionRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommisionServiceImpl implements CommisionService{
	
	@Autowired
	private CommisionRepo commisionRepo;

	@Override
	public Commision saveCommision(double amount, Agent agent, Customer customer, Policy policy) {
		Commision commision = new Commision();
		commision.setAmount(amount);
		commision.setCustomer(customer);
		commision.setAgent(agent);
		commision.setPolicy(policy);
		return commisionRepo.save(commision);
	}
	
}
