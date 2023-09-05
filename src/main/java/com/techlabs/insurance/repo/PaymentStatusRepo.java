package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.PaymentStatus;

public interface PaymentStatusRepo extends JpaRepository<PaymentStatus, Integer>{

}
