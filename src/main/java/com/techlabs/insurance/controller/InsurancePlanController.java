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

import com.techlabs.insurance.entities.InsurancePlan;
import com.techlabs.insurance.service.InsurancePlanService;

@RestController
@RequestMapping("/insurance-app")
public class InsurancePlanController {
	
	@Autowired
	private InsurancePlanService insurancePlanService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/insurance-plan")
	public InsurancePlan saveInsurancePlan(@RequestBody InsurancePlan insurancePlan,@RequestParam(name="statusid") int statusid) {
		return insurancePlanService.saveInsurancePlan(insurancePlan, statusid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/insurance-plan/{planid}")
	InsurancePlan updateInsurancePlan(@RequestBody InsurancePlan insurancePlanData,@PathVariable(name="planid") int planid,@RequestParam(name="statusid") int statusid) {
		return insurancePlanService.updateInsurancePlan(insurancePlanData, planid, statusid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/insurance-plan/{planid}")
	String deleteInsurancePlan(@PathVariable(name="planid") int planid) {
		return insurancePlanService.deleteInsurancePlan(planid);
	}
	
	
	@GetMapping("/insurance-plans")
	public List<InsurancePlan> getInsurancePlans() {
		return insurancePlanService.getInsurancePlans();
	}
	
	@PostMapping("/insurance-plan/togglestatus")
	public void togglePlanStatus(@RequestParam(name="planid")int planId, @RequestParam(name="statusid")int newStatusId) {
		insurancePlanService.togglePlanStatus(planId, newStatusId);
	}
}
