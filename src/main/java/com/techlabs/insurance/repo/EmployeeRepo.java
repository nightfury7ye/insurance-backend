package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

}
