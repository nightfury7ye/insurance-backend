package com.techlabs.insurance.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Integer>{
	List<Payment> findByPolicyPolicyno(int policyno);
}
