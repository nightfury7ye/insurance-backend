package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.InsurancePlan;

public interface InsurancePlanRepo  extends JpaRepository<InsurancePlan, Integer>{
	public boolean existsByPlanname(String planname);

	public boolean existsBySchemename(String schemename);
}
