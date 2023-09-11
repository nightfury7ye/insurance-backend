package com.techlabs.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Employee;
import com.techlabs.insurance.service.AgentService;
import com.techlabs.insurance.service.CustomerService;
import com.techlabs.insurance.service.EmployeeService;

@RestController
@RequestMapping("/insurance-app")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/users/employee")
	Employee saveEmployee(@RequestBody Employee employee, @RequestParam(name="statusid")int statusId) {
		return employeeService.saveEmployee(employee, statusId);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/users/employees")
	public ResponseEntity<Page<Employee>> getAllEmployees(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return employeeService.getAllEmployees(page, size);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/users/employee/{employeeid}")
	public void updateEmployee(@PathVariable(name="employeeid")int employeeId, @RequestBody Employee updatedEmployee) {
		employeeService.updateEmployee(employeeId, updatedEmployee);
	}
	
//	@GetMapping("/users/employee/{employeeid}")
//	public Employee getEmployeeById(@PathVariable(name="employeeid")int employeeId) {
//		return employeeService.getEmployeeById(employeeId);
//	}
//	
	@GetMapping("/users/employee")
	public Employee getEmployeeByUsername(@RequestParam(name="username") String username) {
		return employeeService.getEmployeeByUsername(username);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/users/inactiveemployee/{employeeid}")
	public void inactiveEmployee(@PathVariable(name="employeeid")int employeeId) {
		System.out.println("inactiveEmployee controller");
		employeeService.inactiveEmployee(employeeId);
	}
	
//	@PreAuthorize("hasRole('ADMIN')")
//	@PostMapping("/users/activeemployee/{employeeid}")
//	public void activeEmployee(@PathVariable(name="employeeid")int employeeId) {
//		employeeService.inactiveEmployee(employeeId);
//	}
}
