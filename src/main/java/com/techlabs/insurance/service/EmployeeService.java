package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entities.Employee;

public interface EmployeeService {
	public Employee saveEmployee(Employee employee);
	public Page<Employee> getAllEmployees(int page, int size);
	public Employee updateEmployee(int employeeId, Employee updatedEmployee);
	public void deleteEmployee(int employeeId);
}
