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

import com.techlabs.insurance.entities.Payment;
import com.techlabs.insurance.service.PaymentService;
import com.techlabs.insurance.service.PolicyService;

@RestController
@RequestMapping("/insurance-app")
public class PaymentController {
	
	@Autowired
	private PolicyService policyService;
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/policy/{policyid}/payments")
	public List<Payment> payFirstinstallment(@PathVariable(name="policyid") int policyid,@RequestBody Payment payment){
		return policyService.payFirstinstallment(policyid, payment);
	}
	
	@GetMapping("/policy/{policyno}/payments")
	public List<Payment> getAllpaymentsByPolicy(@PathVariable(name="policyno") int policyno){
		return paymentService.getAllpaymentsByPolicy(policyno);
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/policy/payment/{paymentid}")
	public Payment payInstallment(@RequestBody Payment payment ,@PathVariable(name="paymentid") int paymentid) {
		return policyService.payInstallment(payment, paymentid);
	}
}
