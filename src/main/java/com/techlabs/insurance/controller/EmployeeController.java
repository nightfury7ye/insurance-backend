package com.techlabs.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.User_status;
import com.techlabs.insurance.service.AgentService;
import com.techlabs.insurance.service.CustomerService;

@RestController
@RequestMapping("/employeeapp")
public class EmployeeController {
	@Autowired
	private AgentService agentService;
	@Autowired
	private CustomerService customerService;
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping("/save_agent")
	Agent addAgent(@RequestBody Agent agent) {
		return agentService.addAgent(agent);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@DeleteMapping("/delete_agent/{agentid}")
	public void deleteAgent(@PathVariable(name="agentid")int agentId) {
		agentService.deleteAgent(agentId);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping("/update_agentstatus/{agentid}")
	public Agent updateAgentStatus(@PathVariable(name="agentid")int agentId, @RequestBody User_status userStatus) {
		return agentService.updateAgentStatus(agentId, userStatus);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/get_all_agents")
	public Page<Agent> getAllAgents(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return agentService.getAllAgents(page, size);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/get_all_customers")
	public Page<Customer> getAllCustomers(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return customerService.getAllCustomers(page, size);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@DeleteMapping("/get_customer/{customerid}")
	public void getCustomerById(@PathVariable(name="customerid")int customerId) {
		customerService.getCustomerById(customerId);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@DeleteMapping("/delete_customer/{customerid}")
	public void deleteCustomer(@PathVariable(name="customerid")int customerId) {
		customerService.deleteCustomer(customerId);
	}
}
