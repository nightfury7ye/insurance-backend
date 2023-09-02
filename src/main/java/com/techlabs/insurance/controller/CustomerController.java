package com.techlabs.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@PostMapping("/register_customer")
	public Customer registerCustomer(@RequestBody Customer customer) {
		return customerService.registerCustomer(customer);
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/purchase_policy/{customerid}/{schemeid}/{investtime}/{typeid}/{statusid}")
	Policy purchasePolicy(@RequestBody Policy policy,@PathVariable(name="customerid") int customerid ,@PathVariable(name="schemeid") int schemeid,@PathVariable(name="investtime") int investtime,@PathVariable(name="typeid") int typeid,@PathVariable(name="statusid") int statusid) {
		return policyService.purchasePolicy(policy, customerid, schemeid, investtime, typeid,statusid);
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/pay_first_installment/{policyid}")
	public List<Payment> payFirstinstallment(@PathVariable(name="policyid") int policyid,@RequestBody Payment payment){
		return policyService.payFirstinstallment(policyid, payment);
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/pay_installment/{paymentid}")
	public Payment payInstallment(@RequestBody Payment payment ,@PathVariable(name="paymentid") int paymentid) {
		return policyService.payInstallment(payment, paymentid);
	}
	
}
