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
import com.techlabs.insurance.service.AgentService;
import com.techlabs.insurance.service.CustomerService;

@RestController
@RequestMapping("/insurance-app")
public class AgentController {
	@Autowired
	private AgentService agentService;
	
	@PostMapping("/users/agent") 
	Agent addAgent(@RequestBody Agent agent, @RequestParam(name="statusid")int statusId) {
		return agentService.addAgent(agent, statusId);
	}
	
	@GetMapping("/users/agent/{username}")
	public Agent getAgentByUsername(@PathVariable(name="username") String username) {
		return agentService.getAgentByUsername(username);
	}
	
	@PreAuthorize("hasRole('AGENT')")
	@PutMapping("/users/agent/{username}")
	public Agent updateAgentProfile(@PathVariable(name="username")String username, @RequestBody Agent updatedAgent) {
		return agentService.updateAgentProfile(username, updatedAgent);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@DeleteMapping("/users/agent/{agentid}")
	public void deleteAgent(@PathVariable(name="agentid")int agentId) {
		agentService.deleteAgent(agentId);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/users/agent/{agentid}/status/{statusid}")
	public Agent updateAgentStatus(@PathVariable(name="agentid")int agentId, @PathVariable(name="statusid")int statusId) {
		return agentService.updateAgentStatus(agentId, statusId);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/users/agents")
	public Page<Agent> getAllAgents(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return agentService.getAllAgents(page, size); 
	}
	
}
