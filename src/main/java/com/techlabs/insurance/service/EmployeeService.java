package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entities.Employee;

public interface EmployeeService {
	Employee saveEmployee(Employee employee);
	
	Page<Employee> getAllEmployees(int page, int size);
}
