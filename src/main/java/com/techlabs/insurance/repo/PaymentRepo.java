package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Integer>{

}
