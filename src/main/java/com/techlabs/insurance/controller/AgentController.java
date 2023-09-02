package com.techlabs.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.service.AgentService;
import com.techlabs.insurance.service.CustomerService;

@RestController
@RequestMapping("/agentapp")
public class AgentController {
	@Autowired
	private AgentService agentService;
	@Autowired
	private CustomerService customerService;
	
	@PreAuthorize("hasRole('AGENT')")
	@PostMapping("/register_agent")
	public Agent registerAgent(@RequestBody Agent agent) {
		return agentService.registerAgent(agent);
	}
	
	@PreAuthorize("hasRole('AGENT')")
	@GetMapping("/get_agent/{username}")
	public Agent getAgentByUsername(@PathVariable(name="username") String username) {
		return agentService.getAgentByUsername(username);
	}
	
	@PreAuthorize("hasRole('AGENT')")
	@PutMapping("/update_agent/{username}")
	public Agent updateAgentProfile(@PathVariable(name="username")String username, @RequestBody Agent updatedAgent) {
		return agentService.updateAgentProfile(username, updatedAgent);
	}
	
	@PreAuthorize("hasRole('AGENT')")
	@PostMapping("/register_customer")
	public Customer registerCustomer(@RequestBody Customer customer) {
		return customerService.registerCustomer(customer);
	}
}
