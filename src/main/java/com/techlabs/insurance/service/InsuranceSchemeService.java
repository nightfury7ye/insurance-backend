package com.techlabs.insurance.service;

import com.techlabs.insurance.entities.InsuranceScheme;

public interface InsuranceSchemeService {
	InsuranceScheme saveInsuranceScheme(InsuranceScheme insuranceScheme, int planid, int statusid);

}
