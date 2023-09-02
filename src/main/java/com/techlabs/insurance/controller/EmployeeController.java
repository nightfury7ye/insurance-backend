package com.techlabs.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/employeeapp")
public class EmployeeController {
	@Autowired
	private AgentService agentService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private EmployeeService employeeService;
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping("/saveagent/{statusid}")
	Agent addAgent(@RequestBody Agent agent, @PathVariable(name="statusid")int statusId) {
		return agentService.addAgent(agent, statusId);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@DeleteMapping("/deleteagent/{agentid}")
	public void deleteAgent(@PathVariable(name="agentid")int agentId) {
		agentService.deleteAgent(agentId);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/updateagentstatus/{agentid}/{statusid}")
	public Agent updateAgentStatus(@PathVariable(name="agentid")int agentId, @PathVariable(name="statusid")int statusId) {
		return agentService.updateAgentStatus(agentId, statusId);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/getagents")
	public Page<Agent> getAllAgents(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return agentService.getAllAgents(page, size);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/getcustomers")
	public Page<Customer> getAllCustomers(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return customerService.getAllCustomers(page, size);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/getcustomer/{customerid}")
	public void getCustomerById(@PathVariable(name="customerid")int customerId) {
		customerService.getCustomerById(customerId);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@DeleteMapping("/deletecustomer/{customerid}")
	public void deleteCustomer(@PathVariable(name="customerid")int customerId) {
		customerService.deleteCustomer(customerId);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/updateemployee/{employeeid}")
	public void updateEmployee(@PathVariable(name="employeeid")int employeeId, @RequestBody Employee updatedEmployee) {
		employeeService.updateEmployee(employeeId, updatedEmployee);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/disabledcustomers")
	public Page<Customer> getAllDisabledCustomers(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return customerService.getAllDisabledCustomers(page, size);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/updatecustomerstatus/{customerid}/{statusid}")
	public Customer updateCustomerStatus(@PathVariable(name="customerid")int customerId, @PathVariable(name="statusid")int statusId) {
		return customerService.updateCustomerStatus(customerId, statusId);
	}
}
