package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Role;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.entities.User_status;
import com.techlabs.insurance.repo.AgentRepo;
import com.techlabs.insurance.repo.RoleRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000")
public class AgentServiceImpl implements AgentService{

	@Autowired
	private AgentRepo agentRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public Agent addAgent(Agent agent) {
		User user = agent.getUser();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Optional<Role> userRole= roleRepo.findById(2);
		List<Role> roles = new ArrayList<Role>();
		if(userRole.isPresent()) {
			roles.add(userRole.get());
		}		
		user.setRoles(roles);
		agent.setUser(user);
		return agentRepo.save(agent);
	}

	@Override
	public void deleteAgent(int agentId) {
		agentRepo.deleteById(agentId);
	}

	@Override
	public Agent updateAgentStatus(int agentId, User_status userStatus) {
		Optional<Agent> agent = agentRepo.findById(agentId); 
		Agent agentToUpdate=agent.get();
		agentToUpdate.setUser_status(userStatus);
		return agentRepo.save(agentToUpdate);
	}

	@Override
	public Page<Agent> getAllAgents(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return agentRepo.findAll(pageable);
	}

}
