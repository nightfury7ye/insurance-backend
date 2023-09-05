package com.techlabs.insurance.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.techlabs.insurance.entities.Claim;
import com.techlabs.insurance.entities.Policy;
import com.techlabs.insurance.entities.Status;
import com.techlabs.insurance.exception.PolicyNotFoundException;
import com.techlabs.insurance.repo.ClaimRepo;
import com.techlabs.insurance.repo.PolicyRepo;
import com.techlabs.insurance.repo.StatusRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000")
public class ClaimServiceImpl implements ClaimService{
	@Autowired
	private ClaimRepo claimRepo;
	@Autowired 
	private PolicyRepo policyRepo;
	@Autowired
	private StatusRepo statusRepo;
	
	@Override
	public void addClaim(Claim claim, int statusId) {
		Policy policy = policyRepo.findById(claim.getPolicy().getPolicyno())
                .orElseThrow(() -> new PolicyNotFoundException(HttpStatus.BAD_REQUEST,"Policy not found"));

		if(policy != null) {
			Claim newClaim = new Claim();
	        newClaim.setPolicy(policy);
	        newClaim.setClaim_amount(claim.getClaim_amount());
	        newClaim.setBank_accno(claim.getBank_accno());
	        newClaim.setBank_ifsc_code(claim.getBank_ifsc_code());
	        newClaim.setDate(claim.getDate());
	        
	        Optional<Status> status = statusRepo.findById(statusId);
			if(status.isPresent()) {
				newClaim.setStatus(status.get());
			}else {
				newClaim.setStatus(statusRepo.findById(1).get());
			}
	        
	        claimRepo.save(newClaim);
		}
	}
}
