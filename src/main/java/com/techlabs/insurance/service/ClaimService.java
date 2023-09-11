package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entities.Claim;

public interface ClaimService {
	public void addClaim(Claim claim,int policyno, int statusId);
	
	public Page<Claim> getClaims(int page, int pageSize);
	
	public void approveClaims(int claimid);
}
