package com.techlabs.insurance.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.techlabs.insurance.entities.InsurancePlan;
import com.techlabs.insurance.entities.InsuranceScheme;
import com.techlabs.insurance.entities.SchemeDetails;
import com.techlabs.insurance.entities.Status;
import com.techlabs.insurance.exception.InsurancePlanNotFoundException;
import com.techlabs.insurance.exception.InsuranceSchemeNotFoundException;
import com.techlabs.insurance.exception.PlanAlreadyExistsException;
import com.techlabs.insurance.exception.SchemeAlreadyExistsException;
import com.techlabs.insurance.exception.StatusNotFoundException;
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
	public ResponseEntity<InsuranceScheme> saveInsuranceScheme(InsuranceScheme insuranceScheme, int planid, int statusid) {
		System.out.println("At saveInsuranceScheme Before");
		InsurancePlan insurancePlan = insurancePlanRepo.findById(planid).orElseThrow(()-> new InsurancePlanNotFoundException(HttpStatus.BAD_REQUEST,"Insurance Plan Not Found!!!"));
		Optional<Status> status = statusRepo.findById(statusid);
		
		if(insurancePlanRepo.existsBySchemename(insuranceScheme.getSchemename())) {
			throw new SchemeAlreadyExistsException(HttpStatus.BAD_REQUEST, "Plan Already Exists!!!");
		}
		
		if(status.isPresent()) {
			insuranceScheme.setStatus(status.get());
		}else {
			insuranceScheme.setStatus(statusRepo.findById(1).get());
		}
		insuranceScheme.setPlan(insurancePlan);
		System.out.println("At saveInsuranceScheme After");
		
		insuranceSchemeRepo.save(insuranceScheme);
		return new ResponseEntity<>(insuranceScheme,HttpStatus.OK) ;
	}
	
	@Override
	public String deleteInsuranceScheme(int schemeid) {
		try {
			insuranceSchemeRepo.deleteById(schemeid);
			return "Scheme deleted successfully";
		} catch (Exception e) {
			return "Scheme could not be deleted";
		}
	}

	@Override
	public InsuranceScheme updateInsuranceScheme(InsuranceScheme insuranceSchemeData, int schemeid, int statusid) {
		InsuranceScheme insuranceScheme = insuranceSchemeRepo.findById(schemeid).orElseThrow(()-> new InsuranceSchemeNotFoundException(HttpStatus.BAD_REQUEST,"Insurance Scheme Not Found!!!"));
		Optional<Status> status = statusRepo.findById(statusid);
			InsuranceScheme existingInsuranceScheme = insuranceScheme;
	        
	        existingInsuranceScheme.setSchemename(insuranceSchemeData.getSchemename());
	        
	        SchemeDetails schemeDetailsData = insuranceSchemeData.getSchemeDetails();
	        SchemeDetails existingSchemeDetails = existingInsuranceScheme.getSchemeDetails();
	        
	        existingSchemeDetails.setDiscription(schemeDetailsData.getDiscription());
	        existingSchemeDetails.setMin_amount(schemeDetailsData.getMin_amount());
	        existingSchemeDetails.setMax_amount(schemeDetailsData.getMax_amount());
	        existingSchemeDetails.setMin_invest_time(schemeDetailsData.getMin_invest_time());
	        existingSchemeDetails.setMax_invest_time(schemeDetailsData.getMax_invest_time());
	        existingSchemeDetails.setMin_age(schemeDetailsData.getMin_age());
	        existingSchemeDetails.setMax_age(schemeDetailsData.getMax_age());
	        existingSchemeDetails.setProfit_ratio(schemeDetailsData.getProfit_ratio());
	        existingSchemeDetails.setRegistrationcommratio(schemeDetailsData.getRegistrationcommratio());
	        
	        existingInsuranceScheme.setStatus(insuranceSchemeData.getStatus());
	        if (status.isPresent()) {
	            existingInsuranceScheme.setStatus(status.get());
	        } else {
	            existingInsuranceScheme.setStatus(statusRepo.findById(1).get());
	        }
	        
	        return insuranceSchemeRepo.save(existingInsuranceScheme);
	}

	@Override
	public void toggleSchemeStatus(int schemeId, int newStatusId) {
		InsuranceScheme scheme = insuranceSchemeRepo.findById(schemeId)
                .orElseThrow(() -> new InsuranceSchemeNotFoundException(HttpStatus.BAD_REQUEST, "Scheme not found with id: " + schemeId));

        if(scheme != null) {
        	Status newStatus = statusRepo.findById(newStatusId).orElseThrow(() -> new StatusNotFoundException(HttpStatus.BAD_REQUEST, "Scheme status not found with id: " + newStatusId));
        	
        	if(newStatus != null) {
            	scheme.setStatus(newStatus);
        	}
        	insuranceSchemeRepo.save(scheme);
        }		
		
	}
}
