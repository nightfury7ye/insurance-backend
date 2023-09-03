package com.techlabs.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entities.Policy;
import com.techlabs.insurance.service.PolicyService;

@RestController
@RequestMapping("/insurance-app")
public class PolicyController {
	@Autowired
	private PolicyService policyService;
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("customer/{customerid}/policies")
	public ResponseEntity<Page<Policy>> getPoliciesByCustomer(@PathVariable(name="customerid") int customerid, @RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		Page<Policy> policies = policyService.getPoliciesByCustomer(customerid, page, size);
		if(!policies.isEmpty())
			return new ResponseEntity<>(policies, HttpStatus.OK);
		return null; 
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("customer/policy/{policyid}")
	public ResponseEntity<Policy> getPolicy(@PathVariable(name="policyid") int policyid){
		Policy policy = policyService.getPolicy(policyid);
		if(policy != null)
			return new ResponseEntity<>(policy, HttpStatus.OK);
		return null; 
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("customer/{customerid}/policy")
	Policy purchasePolicy(@RequestBody Policy policy,@PathVariable(name="customerid") int customerid ,@RequestParam(name="schemeid") int schemeid,@RequestParam(name="investtime") int investtime,@RequestParam(name="typeid") int typeid,@RequestParam(name="statusid") int statusid) {
		return policyService.purchasePolicy(policy, customerid, schemeid, investtime, typeid,statusid);
	}
}
