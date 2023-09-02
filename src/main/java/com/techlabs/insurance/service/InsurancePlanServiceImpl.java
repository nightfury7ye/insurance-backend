package com.techlabs.insurance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.IntArraySerializer;
import com.techlabs.insurance.entities.InsurancePlan;
import com.techlabs.insurance.entities.InsuranceScheme;
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

	@Override
	public String deleteInsurancePlan(int planid) {
		try {
			insurancePlanRepo.deleteById(planid);
			return "Plan deleted successfully";
		} catch (Exception e) {
			return "Plan could not be deleted";
		}
	}

	@Override
	public InsurancePlan updateInsurancePlan(InsurancePlan insurancePlanData, int planid, int statusid) {
		Optional<InsurancePlan> insurancePlan = insurancePlanRepo.findById(planid);
		Optional<Status> status = statusRepo.findById(statusid);
		if(insurancePlan.isPresent()) {
			insurancePlan.get().setPlan_name(insurancePlanData.getPlan_name());
			if(status.isPresent()) {
				insurancePlan.get().setStatus(status.get());
			}else {
				insurancePlan.get().setStatus(statusRepo.findById(1).get());
			}
			return insurancePlanRepo.save(insurancePlan.get());
		}
		return null;
	}

	@Override
	public List<InsuranceScheme> getInsuranceSchemeById(int planid) {
		Optional<InsurancePlan> insurancePlan = insurancePlanRepo.findById(planid);
		if(insurancePlan.isPresent()) {
			return insurancePlan.get().getSchemes();
		}
		return null;
	}

}
