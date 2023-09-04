package com.techlabs.insurance.service;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Commision;
import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Policy;

public interface CommisionService {
	Commision saveCommision(double amount, Agent agent, Customer customer, Policy policy);
}
