package com.techlabs.insurance.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.techlabs.insurance.entities.Claim;
import com.techlabs.insurance.entities.Commision;
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
	public void addClaim(Claim claim, int policyno, int statusId) {
		Policy policy = policyRepo.findById(policyno)
                .orElseThrow(() -> new PolicyNotFoundException(HttpStatus.BAD_REQUEST,"Policy not found"));
		LocalDate currentDate = LocalDate.now();
		Date date = Date.valueOf(currentDate);
		if(policy != null) {
			Claim newClaim = new Claim();
	        newClaim.setPolicy(policy);
	        newClaim.setClaim_amount(claim.getClaim_amount());
	        newClaim.setBank_accno(claim.getBank_accno());
	        newClaim.setBank_ifsc_code(claim.getBank_ifsc_code());
	        newClaim.setRequestStatus("pending");
	        newClaim.setDate(date);
	        
	        Optional<Status> status = statusRepo.findById(statusId);
			if(status.isPresent()) {
				newClaim.setStatus(status.get());
			}else {
				newClaim.setStatus(statusRepo.findById(1).get());
			}
	        
	        claimRepo.save(newClaim);
		}
	}

	@Override
	public Page<Claim> getClaims(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<Claim> claims = claimRepo.findAll(pageable);
		return claims;
	}

	@Override
	public void approveClaims(int claimid) {
		Claim claim = claimRepo.findById(claimid).orElseThrow();
		claim.setRequestStatus("approved");
		claimRepo.save(claim);
	}
}
