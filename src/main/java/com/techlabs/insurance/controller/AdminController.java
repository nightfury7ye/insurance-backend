package com.techlabs.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entities.Employee;
import com.techlabs.insurance.entities.InsurancePlan;
import com.techlabs.insurance.entities.InsuranceScheme;
import com.techlabs.insurance.service.EmployeeService;
import com.techlabs.insurance.service.InsurancePlanService;
import com.techlabs.insurance.service.InsuranceSchemeService;

@RestController
@RequestMapping("/adminapp")
public class AdminController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private InsurancePlanService insurancePlanService;
	@Autowired
	private InsuranceSchemeService insuranceSchemeService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save_employee")
	Employee saveEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save_insuranceplan/{statusid}")
	InsurancePlan saveInsurancePlan(@RequestBody InsurancePlan insurancePlan,@PathVariable(name="statusid") int statusid) {
		return insurancePlanService.saveInsurancePlan(insurancePlan, statusid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save_insurance_scheme/{planid}/{statusid}")
	InsuranceScheme saveInsuranceScheme(@RequestBody InsuranceScheme insuranceScheme,@PathVariable(name="planid") int planid,@PathVariable(name="statusid") int statusid) {
		return insuranceSchemeService.saveInsuranceScheme(insuranceScheme, planid, statusid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get_insurance_plans")
	List<InsurancePlan> getInsurancePlans() {
		return insurancePlanService.getInsurancePlans();
	}
}
