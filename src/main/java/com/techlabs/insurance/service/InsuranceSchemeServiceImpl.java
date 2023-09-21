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
import com.techlabs.insurance.exception.InvalidAgeException;
import com.techlabs.insurance.exception.InvalidAmountException;
import com.techlabs.insurance.exception.InvalidInvestTimeException;
import com.techlabs.insurance.exception.InvalidProfitRatioException;
import com.techlabs.insurance.exception.InvalidRegistrationCommissionRatioException;
import com.techlabs.insurance.exception.PlanAlreadyExistsException;
import com.techlabs.insurance.exception.SchemeAlreadyExistsException;
import com.techlabs.insurance.exception.StatusNotFoundException;
import com.techlabs.insurance.repo.InsurancePlanRepo;
import com.techlabs.insurance.repo.InsuranceSchemeRepo;
import com.techlabs.insurance.repo.SchemeDetailsRepo;
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
	@Autowired
	private SchemeDetailsRepo schemeDetailsRepo;

	@Override
	public ResponseEntity<InsuranceScheme> saveInsuranceScheme(InsuranceScheme insuranceScheme, int planid, int statusid) {
		System.out.println("At saveInsuranceScheme Before");
		InsurancePlan insurancePlan = insurancePlanRepo.findById(planid).orElseThrow(()-> new InsurancePlanNotFoundException(HttpStatus.BAD_REQUEST,"Insurance Plan Not Found!!!"));
		Optional<Status> status = statusRepo.findById(statusid);
		
		int maxAge = insuranceScheme.getSchemeDetails().getMax_age();
		int minAge = insuranceScheme.getSchemeDetails().getMin_age();
		double minAmount = insuranceScheme.getSchemeDetails().getMin_amount();
		double maxAmount = insuranceScheme.getSchemeDetails().getMax_amount();
		int minInvestTime = insuranceScheme.getSchemeDetails().getMin_invest_time();
		int maxInvestTime = insuranceScheme.getSchemeDetails().getMax_invest_time();
		int registrationCommRatio = insuranceScheme.getSchemeDetails().getRegistrationcommratio();
		int profitRatio = insuranceScheme.getSchemeDetails().getProfit_ratio();
		
		if(minAge >= maxAge) {
			throw new InvalidAgeException(HttpStatus.BAD_REQUEST, "Age Is Invalid!!!");
		}
		
		if(insurancePlanRepo.existsBySchemesSchemename(insuranceScheme.getSchemename())) {
			throw new SchemeAlreadyExistsException(HttpStatus.BAD_REQUEST, "Scheme Already Exists!!!");
		}
		
		if(minAmount >= maxAmount) {
			throw new InvalidAmountException(HttpStatus.BAD_REQUEST, "Amount is invalid");
		}
		
		if(minInvestTime >= maxInvestTime) {
			throw new InvalidInvestTimeException(HttpStatus.BAD_REQUEST, "Invest time is invalid");
		}
		
		if(registrationCommRatio < 2 || registrationCommRatio > 8) {
			throw new InvalidRegistrationCommissionRatioException(HttpStatus.BAD_REQUEST, "Commission registration ratio is invalid");
		}
		
		if(profitRatio < 0 && profitRatio > 20) {
			throw new InvalidProfitRatioException(HttpStatus.BAD_REQUEST, "Profit ratio is invalid");
		}
		
		if(status.isPresent()) {
			insuranceScheme.setStatus(status.get());
		}else {
			insuranceScheme.setStatus(statusRepo.findById(1).get());
		}
		insuranceScheme.setPlan(insurancePlan);
		System.out.println("At saveInsuranceScheme After");
		
		insuranceSchemeRepo.save(insuranceScheme);
		return new ResponseEntity<>(insuranceScheme,HttpStatus.OK);
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
	public ResponseEntity<InsuranceScheme> updateInsuranceScheme(InsuranceScheme insuranceSchemeData, int schemeid, int statusid) {
		InsuranceScheme insuranceScheme = insuranceSchemeRepo.findById(schemeid).orElseThrow(()-> new InsuranceSchemeNotFoundException(HttpStatus.BAD_REQUEST,"Insurance Scheme Not Found!!!"));
		Optional<Status> status = statusRepo.findById(statusid);
			InsuranceScheme existingInsuranceScheme = insuranceScheme;
	        
	        existingInsuranceScheme.setSchemename(insuranceSchemeData.getSchemename());
	        
	        SchemeDetails schemeDetailsData = insuranceSchemeData.getSchemeDetails();
	        SchemeDetails existingSchemeDetails = existingInsuranceScheme.getSchemeDetails();
	        
	        int maxAge = schemeDetailsData.getMax_age();
			int minAge = schemeDetailsData.getMin_age();
			double minAmount = schemeDetailsData.getMin_amount();
			double maxAmount = schemeDetailsData.getMax_amount();
			int minInvestTime = schemeDetailsData.getMin_invest_time();
			int maxInvestTime = schemeDetailsData.getMax_invest_time();
			int registrationCommRatio = schemeDetailsData.getRegistrationcommratio();
			int profitRatio = schemeDetailsData.getProfit_ratio();
			
			if(minAge >= maxAge) {
				throw new InvalidAgeException(HttpStatus.BAD_REQUEST, "Age Is Invalid!!!");
			}
			
			if(insurancePlanRepo.existsBySchemesSchemename(insuranceScheme.getSchemename())) {
				throw new SchemeAlreadyExistsException(HttpStatus.BAD_REQUEST, "Scheme Already Exists!!!");
			}
			
			if(minAmount >= maxAmount) {
				throw new InvalidAmountException(HttpStatus.BAD_REQUEST, "Amount is invalid");
			}
			
			if(minInvestTime >= maxInvestTime) {
				throw new InvalidInvestTimeException(HttpStatus.BAD_REQUEST, "Invest time is invalid");
			}
			
			if(registrationCommRatio < 2 || registrationCommRatio > 8) {
				throw new InvalidRegistrationCommissionRatioException(HttpStatus.BAD_REQUEST, "Commission registration ratio is invalid");
			}
			
			if(profitRatio < 0 && profitRatio > 20) {
				throw new InvalidProfitRatioException(HttpStatus.BAD_REQUEST, "Profit ratio is invalid");
			}
	        
	        existingSchemeDetails.setDiscription(schemeDetailsData.getDiscription());
	        existingSchemeDetails.setMin_amount(minAmount);
	        existingSchemeDetails.setMax_amount(maxAmount);
	        existingSchemeDetails.setMin_invest_time(minInvestTime);
	        existingSchemeDetails.setMax_invest_time(maxInvestTime);
	        existingSchemeDetails.setMin_age(minAge);
	        existingSchemeDetails.setMax_age(maxAge);
	        existingSchemeDetails.setProfit_ratio(profitRatio);
	        existingSchemeDetails.setRegistrationcommratio(registrationCommRatio);
	        
	        existingInsuranceScheme.setStatus(insuranceSchemeData.getStatus());
	        if (status.isPresent()) {
	            existingInsuranceScheme.setStatus(status.get());
	        } else {
	            existingInsuranceScheme.setStatus(statusRepo.findById(1).get());
	        }
	        
	        insuranceSchemeRepo.save(existingInsuranceScheme);
	        return new ResponseEntity<>(existingInsuranceScheme,HttpStatus.OK);
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
