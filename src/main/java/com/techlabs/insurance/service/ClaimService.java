package com.techlabs.insurance.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.techlabs.insurance.entities.Claim;

public interface ClaimService {
	public void addClaim(Claim claim, int statusId);
}
