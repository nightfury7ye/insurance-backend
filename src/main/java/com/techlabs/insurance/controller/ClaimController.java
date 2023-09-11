package com.techlabs.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/claim")
	public void addClaim(@RequestBody Claim claim,@RequestParam int policyno, @RequestParam int statusId) {
		claimService.addClaim(claim, policyno, statusId);
	}
	
	@GetMapping("/claims")
	public Page<Claim> getClaims(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return claimService.getClaims(page, size);
	}
	
	@PutMapping("/claim/{claimid}/approve")
	public void approveClaims(@PathVariable(name="claimid")  int claimid) {
		claimService.approveClaims(claimid);
	}
}
