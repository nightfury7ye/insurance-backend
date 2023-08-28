package com.techlabs.insurance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.techlabs.insurance.entities.InsurancePlan;
import com.techlabs.insurance.entities.Status;
import com.techlabs.insurance.repo.InsurancePlanRepo;
import com.techlabs.insurance.repo.StatusRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000")
public class InsurancePlanServiceImpl implements InsurancePlanService{
	@Autowired
	private InsurancePlanRepo insurancePlanRepo;
	@Autowired
	private StatusRepo statusRepo;

	@Override
	public InsurancePlan saveInsurancePlan(InsurancePlan insurancePlan, int statusid) {
		Optional<Status> status = statusRepo.findById(statusid);
		if(status.isPresent()) {
			insurancePlan.setStatus(status.get());
		}else {
			insurancePlan.setStatus(statusRepo.findById(1).get());
		}
		return insurancePlanRepo.save(insurancePlan);
	}

	@Override
	public List<InsurancePlan> getInsurancePlans() {
		return insurancePlanRepo.findAll(); 
	}

}
