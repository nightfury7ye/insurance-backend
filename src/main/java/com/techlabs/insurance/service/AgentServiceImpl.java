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
import com.techlabs.insurance.payload.RegisterDto;
import com.techlabs.insurance.repo.AgentRepo;
import com.techlabs.insurance.repo.CustomerRepo;
import com.techlabs.insurance.repo.RoleRepo;
import com.techlabs.insurance.repo.UserRepo;
import com.techlabs.insurance.repo.UserStatusRepo;

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
	@Autowired
	private UserStatusRepo userStatusRepo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public Agent addAgent(Agent agent, int statusId) {
		User user = agent.getUser();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Optional<Role> userRole= roleRepo.findById(2);
		List<Role> roles = new ArrayList<Role>();
		if(userRole.isPresent()) {
			roles.add(userRole.get());
		}		
		user.setRoles(roles);
		agent.setUser(user);
		
		Optional<User_status> status = userStatusRepo.findById(statusId);
		if(status.isPresent()) {
			agent.setUser_status(status.get());
		}else {
			agent.setUser_status(userStatusRepo.findById(1).get());
		}
		return agentRepo.save(agent);
	}

	@Override
	public void deleteAgent(int agentId) {
		agentRepo.deleteById(agentId);
	}

	@Override
	public Agent updateAgentStatus(int agentId, int statusId) {
		Optional<Agent> agent = agentRepo.findById(agentId);
		
		Optional<User_status> status = userStatusRepo.findById(statusId);
		if(status.isPresent()) {
			agent.get().setUser_status(status.get());
		}else {
			agent.get().setUser_status(userStatusRepo.findById(1).get());
		}
		
		return agentRepo.save(agent.get());
	}

	@Override
	public Page<Agent> getAllAgents(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return agentRepo.findAll(pageable);
	}

	@Override
	public Agent registerAgent(Agent agent) {
		RegisterDto registerDto = new RegisterDto();
		registerDto.setUsername(agent.getUser().getUsername());
		registerDto.setPassword(agent.getUser().getPassword());
		User user = authService.register(registerDto, 1);
		agent.setUser(user);
		return agentRepo.save(agent);
	}

	@Override
	public Agent getAgentByUsername(String username) {
		return agentRepo.findByUserUsername(username);
	}

	@Override
	public Agent updateAgentProfile(String username, Agent updatedAgent) {
		Agent existingAgent = agentRepo.findByUserUsername(username);
		if(existingAgent != null) {
			existingAgent.setFirstname(updatedAgent.getFirstname());
			existingAgent.setLastname(updatedAgent.getLastname());
			existingAgent.setQualification(updatedAgent.getQualification());
			
			User existingUser = userRepo.findById(existingAgent.getUser().getUserid()).orElse(null);
			if(existingUser != null) {
				existingUser.setUsername(updatedAgent.getUser().getUsername());
				if(updatedAgent.getUser().getPassword() != null) {
					existingUser.setPassword(passwordEncoder.encode(updatedAgent.getUser().getPassword()));
				}
				existingAgent.setUser(existingUser);
			}
		}
		return agentRepo.save(existingAgent);
	}

}
