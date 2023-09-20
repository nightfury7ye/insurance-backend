package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.InsurancePlan;

public interface InsurancePlanRepo  extends JpaRepository<InsurancePlan, Integer>{
//	public boolean existsByPlan_name(String planname);

//	public boolean existsBySchemesScheme_name(String schemename);
}
