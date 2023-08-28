package com.techlabs.insurance.service;

import java.util.List;

import com.techlabs.insurance.entities.InsurancePlan;

public interface InsurancePlanService {
	InsurancePlan saveInsurancePlan(InsurancePlan insurancePlan, int statusid);
	
	List<InsurancePlan> getInsurancePlans();
}
