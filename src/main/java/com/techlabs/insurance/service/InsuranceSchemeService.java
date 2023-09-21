package com.techlabs.insurance.service;

import org.springframework.http.ResponseEntity;

import com.techlabs.insurance.entities.InsuranceScheme;

public interface InsuranceSchemeService {
	public ResponseEntity<InsuranceScheme> saveInsuranceScheme(InsuranceScheme insuranceScheme, int planid, int statusid);

	public String deleteInsuranceScheme(int schemeid);
	
	public ResponseEntity<InsuranceScheme> updateInsuranceScheme(InsuranceScheme insuranceSchemeData, int schemeid, int statusid);
	
	public void toggleSchemeStatus(int schemeId, int newStatusId);
}
