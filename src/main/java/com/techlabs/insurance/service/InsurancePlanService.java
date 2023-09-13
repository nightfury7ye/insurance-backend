package com.techlabs.insurance.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.techlabs.insurance.entities.InsurancePlan;
import com.techlabs.insurance.entities.InsuranceScheme;

public interface InsurancePlanService {
	ResponseEntity<InsurancePlan> saveInsurancePlan(InsurancePlan insurancePlan, int statusid);
	
	String deleteInsurancePlan(int planid);
	
	List<InsurancePlan> getInsurancePlans();
	
	InsurancePlan updateInsurancePlan(InsurancePlan insurancePlan, int statusid, int planid);
	
	List<InsuranceScheme> getInsuranceSchemeById(int planid);
	
	public void togglePlanStatus(int planId, int statusId);
}
