package com.techlabs.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entities.InsurancePlan;
import com.techlabs.insurance.entities.InsuranceScheme;
import com.techlabs.insurance.service.InsurancePlanService;

@RestController
@RequestMapping("/servicesapp")
public class CommonController {
	@Autowired
	private InsurancePlanService insurancePlanService;
	
	
	@GetMapping("/get_plans")
	public List<InsurancePlan> getInsurancePlans() {
		return insurancePlanService.getInsurancePlans();
	}
	
	@GetMapping("/get_scheme_by_planid/{planid}")
	List<InsuranceScheme> getInsuranceSchemeById(@PathVariable(name="planid") int planid){
		return insurancePlanService.getInsuranceSchemeById(planid);
	}
}
