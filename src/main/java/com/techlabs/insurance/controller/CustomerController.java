package com.techlabs.insurance.controller;

import java.util.List;

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

import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Payment;
import com.techlabs.insurance.entities.Policy;
import com.techlabs.insurance.service.CustomerService;
import com.techlabs.insurance.service.PolicyService;

@RestController
@RequestMapping("/customerapp")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private PolicyService policyService;
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/registercustomer")
	public Customer registerCustomer(@RequestBody Customer customer) {
		return customerService.registerCustomer(customer);
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/getcustomer/{username}")
	public Customer getCustomerByUsername(@PathVariable(name="username") String username) {
		return customerService.getCustomerByUsername(username);
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/getpolicies/{customerid}")
	public ResponseEntity<Page<Policy>> getPoliciesByCustomer(@PathVariable(name="customerid") int customerid, @RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		Page<Policy> policies = policyService.getPoliciesByCustomer(customerid, page, size);
		if(!policies.isEmpty())
			return new ResponseEntity<>(policies, HttpStatus.OK);
		return null; 
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/purchasepolicy/{customerid}/{schemeid}/{investtime}/{typeid}/{statusid}")
	Policy purchasePolicy(@RequestBody Policy policy,@PathVariable(name="customerid") int customerid ,@PathVariable(name="schemeid") int schemeid,@PathVariable(name="investtime") int investtime,@PathVariable(name="typeid") int typeid,@PathVariable(name="statusid") int statusid) {
		return policyService.purchasePolicy(policy, customerid, schemeid, investtime, typeid,statusid);
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/firstinstallment/{policyid}")
	public List<Payment> payFirstInstallment(@PathVariable(name="policyid") int policyid,@RequestBody Payment payment){
		return policyService.payFirstInstallment(policyid, payment);
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/payinstallment/{paymentid}")
	public Payment payInstallment(@RequestBody Payment payment ,@PathVariable(name="paymentid") int paymentid) {
		return policyService.payInstallment(payment, paymentid);
	}
	
}
