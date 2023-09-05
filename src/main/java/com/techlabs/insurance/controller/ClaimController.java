package com.techlabs.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entities.Claim;
import com.techlabs.insurance.service.ClaimService;

@RestController
@RequestMapping("/insurance-app")
public class ClaimController {
	@Autowired
	private ClaimService claimService;
	
	@PostMapping("/claim/{statusid}")
	public void addClaim(@RequestBody Claim claim, @PathVariable(name="statusid") int statusId) {
		claimService.addClaim(claim, statusId);
	}
}
