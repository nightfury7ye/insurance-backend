package com.techlabs.insurance.service;

import com.techlabs.insurance.entities.InsuranceScheme;

public interface InsuranceSchemeService {
	public InsuranceScheme saveInsuranceScheme(InsuranceScheme insuranceScheme, int planid, int statusid);

	public String deleteInsuranceScheme(int schemeid);
	
	public InsuranceScheme updateInsuranceScheme(InsuranceScheme insuranceSchemeData, int schemeid, int statusid);
	
	public void toggleSchemeStatus(int schemeId, int newStatusId);
}
