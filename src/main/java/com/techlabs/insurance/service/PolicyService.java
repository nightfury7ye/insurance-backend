package com.techlabs.insurance.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entities.Payment;
import com.techlabs.insurance.entities.Policy;

public interface PolicyService {
	Policy purchasePolicy(Policy policy,int customerid ,int schemeid,int investtime, int typeid, int statusid);
	
	List<Payment> payFirstInstallment(int policyid, Payment payment);
	
	Payment payInstallment(Payment payment ,int paymentid);
	
	Page<Policy> getPoliciesByCustomer(int customerid, int pageno, int pagesize);
}
