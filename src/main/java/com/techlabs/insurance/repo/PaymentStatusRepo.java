package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Payment_status;

public interface PaymentStatusRepo extends JpaRepository<Payment_status, Integer>{

}
