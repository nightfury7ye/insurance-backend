package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.InstallmentType;
import com.techlabs.insurance.entities.InsuranceScheme;

public interface InstallmentTypeRepo extends JpaRepository<InstallmentType, Integer>{

}
