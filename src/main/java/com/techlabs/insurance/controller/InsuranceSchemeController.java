package com.techlabs.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entities.InsuranceScheme;
import com.techlabs.insurance.service.InsurancePlanService;
import com.techlabs.insurance.service.InsuranceSchemeService;

@RestController
@RequestMapping("/insurance-app")
public class InsuranceSchemeController {
	
	@Autowired
	private InsuranceSchemeService insuranceSchemeService;
	@Autowired
	private InsurancePlanService insurancePlanService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/insurance-scheme/{schemeid}")
	InsuranceScheme updateInsuranceScheme(@RequestBody InsuranceScheme insuranceSchemeData,@PathVariable(name="schemeid") int schemeid,@RequestParam(name="statusid") int statusid) {
		return insuranceSchemeService.updateInsuranceScheme(insuranceSchemeData, schemeid, statusid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/insurance-scheme/{schemeid}")
	public String deleteInsuranceScheme(@PathVariable(name="schemeid") int schemeid) {
		return insuranceSchemeService.deleteInsuranceScheme(schemeid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/insurance-plan/{planid}/insurance-scheme/{statusid}")
	public InsuranceScheme saveInsuranceScheme(@RequestBody InsuranceScheme insuranceScheme,@PathVariable(name="planid") int planid,@RequestParam(name="statusid") int statusid) {
		return insuranceSchemeService.saveInsuranceScheme(insuranceScheme, planid, statusid);
	}
	
	@GetMapping("/insurance-plan/{planid}/insurance-schemes")
	List<InsuranceScheme> getInsuranceSchemeById(@PathVariable(name="planid") int planid){
		return insurancePlanService.getInsuranceSchemeById(planid);
	}
	
	@PostMapping("/insurance-scheme/{schemeid}/{statusid}")
	public void toggleSchemeStatus(@PathVariable(name="schemeid")int schemeId,@PathVariable(name="statusid") int newStatusId) {
		insuranceSchemeService.toggleSchemeStatus(schemeId, newStatusId);
	}
}
