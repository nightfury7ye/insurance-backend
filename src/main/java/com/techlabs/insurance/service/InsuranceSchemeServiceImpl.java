package com.techlabs.insurance.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.techlabs.insurance.entities.InsurancePlan;
import com.techlabs.insurance.entities.InsuranceScheme;
import com.techlabs.insurance.entities.Status;
import com.techlabs.insurance.repo.InsurancePlanRepo;
import com.techlabs.insurance.repo.InsuranceSchemeRepo;
import com.techlabs.insurance.repo.StatusRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000")
public class InsuranceSchemeServiceImpl implements InsuranceSchemeService{
	@Autowired
	private InsuranceSchemeRepo insuranceSchemeRepo;
	@Autowired
	private InsurancePlanRepo insurancePlanRepo;
	@Autowired
	private StatusRepo statusRepo;

	@Override
	public InsuranceScheme saveInsuranceScheme(InsuranceScheme insuranceScheme, int planid, int statusid) {
		System.out.println("At saveInsuranceScheme Before");
		Optional<InsurancePlan> insurancePlan = insurancePlanRepo.findById(planid);
		Optional<Status> status = statusRepo.findById(statusid);
		if(status.isPresent()) {
			insuranceScheme.setStatus(status.get());
		}else {
			insuranceScheme.setStatus(statusRepo.findById(1).get());
		}
		if(insurancePlan.isPresent()) {
			insuranceScheme.setPlan(insurancePlan.get());
			System.out.println("At saveInsuranceScheme After");
			return insuranceSchemeRepo.save(insuranceScheme);
		}
		return null;
	}
}
