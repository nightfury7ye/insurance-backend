package com.techlabs.insurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entities.Payment;
import com.techlabs.insurance.repo.PaymentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private PaymentRepo paymentRepo;

	@Override
	public List<Payment> getAllpaymentsByPolicy(int policyno) {
		return paymentRepo.findByPolicyPolicyno(policyno);
	}
	
}
