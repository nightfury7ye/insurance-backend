package com.techlabs.insurance.service;

import java.util.List;

import com.techlabs.insurance.entities.Payment;

public interface PaymentService {
	List<Payment> getAllpaymentsByPolicy(int policyno);
}
