package com.techlabs.insurance.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.IntArraySerializer;
import com.techlabs.insurance.entities.InsurancePlan;
import com.techlabs.insurance.entities.InsuranceScheme;
import com.techlabs.insurance.entities.Status;
import com.techlabs.insurance.entities.UserStatus;
import com.techlabs.insurance.exception.InsurancePlanNotFoundException;
import com.techlabs.insurance.exception.InsuranceSchemeNotFoundException;
import com.techlabs.insurance.exception.InvalidFirstnameException;
import com.techlabs.insurance.exception.InvalidPlannameException;
import com.techlabs.insurance.exception.PlanAlreadyExistsException;
import com.techlabs.insurance.exception.StatusNotFoundException;
import com.techlabs.insurance.exception.UserAPIException;
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
	public ResponseEntity<InsurancePlan> saveInsurancePlan(InsurancePlan insurancePlan, int statusid) {
		Optional<Status> status = statusRepo.findById(statusid);
		
		String planname = insurancePlan.getPlanname();
		
		if(insurancePlanRepo.existsByPlanname(planname)) {
			throw new PlanAlreadyExistsException(HttpStatus.BAD_REQUEST, "Plan Already Exists!!!");
		}
		
		if(!isValidPlanname(planname)) {
	    	throw new InvalidPlannameException(HttpStatus.BAD_REQUEST, "Invalid planname format");
	    }
	    
		if(status.isPresent()) {
			insurancePlan.setStatus(status.get());
		}else {
			insurancePlan.setStatus(statusRepo.findById(1).get()); 
		}
		insurancePlanRepo.save(insurancePlan);
		return new ResponseEntity<>(insurancePlan,HttpStatus.OK) ;
	}

	private boolean isValidPlanname(String planname) {
		String regex = "^[A-Za-z][A-Za-z\\s]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(planname);
		return matcher.matches();
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
		InsurancePlan insurancePlan = insurancePlanRepo.findById(planid).orElseThrow(()-> new InsurancePlanNotFoundException(HttpStatus.BAD_REQUEST,"Insurance Plan Not Found!!!"));
		Optional<Status> status = statusRepo.findById(statusid);
		
		String planname = insurancePlanData.getPlanname();
		
		if(insurancePlanRepo.existsByPlanname(planname)) {
			throw new PlanAlreadyExistsException(HttpStatus.BAD_REQUEST, "Plan Already Exists!!!");
		}
		
		if(!isValidPlanname(planname)) {
	    	throw new InvalidPlannameException(HttpStatus.BAD_REQUEST, "Invalid planname format");
	    }
		
		insurancePlan.setPlanname(planname);
			
		if(status.isPresent()) {
			insurancePlan.setStatus(status.get());
		}else {
			insurancePlan.setStatus(statusRepo.findById(1).get());
			}
		return insurancePlanRepo.save(insurancePlan);
	}

	@Override
	public List<InsuranceScheme> getInsuranceSchemeById(int planid) {
		InsurancePlan insurancePlan = insurancePlanRepo.findById(planid).orElseThrow(()-> new InsurancePlanNotFoundException(HttpStatus.BAD_REQUEST,"Insurance Plan Not Found!!!"));
		
		return insurancePlan.getSchemes();
	}

	@Override
	public void togglePlanStatus(int planId, int newStatusId) {
		InsurancePlan plan = insurancePlanRepo.findById(planId)
                .orElseThrow(() -> new InsurancePlanNotFoundException(HttpStatus.BAD_REQUEST, "Plan not found with id: " + planId));

        if(plan != null) {
        	Status newStatus = statusRepo.findById(newStatusId).orElseThrow(() -> new StatusNotFoundException(HttpStatus.BAD_REQUEST, "Plan status not found with id: " + newStatusId));
        	
        	if(newStatus != null) {
            	plan.setStatus(newStatus);
        	}
        	insurancePlanRepo.save(plan);
        }		
	}
}
