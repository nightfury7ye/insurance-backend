package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Admin;
import com.techlabs.insurance.entities.Employee;

public interface AdminRepo extends JpaRepository<Admin, Integer>{
	public Admin findByUserUsername(String username);
}
