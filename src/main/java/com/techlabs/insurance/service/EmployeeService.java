package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.techlabs.insurance.entities.Employee;

public interface EmployeeService {
	public Employee saveEmployee(Employee employee,int statusId);
	public ResponseEntity<Page<Employee>> getAllEmployees(int page, int size);
	public Employee updateEmployee(int employeeId, Employee updatedEmployee);
	public void inactiveEmployee(int employeeId);
	public void activeEmployee(int employeeId);
}
