package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Role;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.entities.UserStatus;
import com.techlabs.insurance.exception.ListIsEmptyException;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.exception.UsernameAlreadyExistsException;
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
	public ResponseEntity<Agent> addAgent(Agent agent, int statusId) {
		User user = agent.getUser();
		
		if(userRepo.existsByUsername(agent.getUser().getUsername())) {
			throw new UsernameAlreadyExistsException("Username already exists!!!", HttpStatus.BAD_REQUEST);
		}
		user.setUsername(user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Optional<Role> userRole= roleRepo.findById(2);
		List<Role> roles = new ArrayList<Role>();
		if(userRole.isPresent()) {
			roles.add(userRole.get());
		}		
		user.setRoles(roles);
		agent.setUser(user);
		
		Optional<UserStatus> status = userStatusRepo.findById(statusId);
		if(status.isPresent()) {
			agent.setUserStatus(status.get());
		}else {
			agent.setUserStatus(userStatusRepo.findById(1).get());
		}
		agentRepo.save(agent);
		return new ResponseEntity<>(agent,HttpStatus.OK) ;
	}

	@Override
	public void deleteAgent(int agentId) {
		agentRepo.deleteById(agentId);
	}

	@Override
	public Agent updateAgentStatus(int agentId, int statusId) {
		Agent agent = agentRepo.findById(agentId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Agent Not Found!!!"));
		
		Optional<UserStatus> status = userStatusRepo.findById(statusId);
		if(status.isPresent()) {
			agent.setUserStatus(status.get());
		}else {
			agent.setUserStatus(userStatusRepo.findById(1).get());
		}
		
		return agentRepo.save(agent);
	}

	@Override
	public ResponseEntity<Page<Agent>> getAllAgents(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Agent> agents =agentRepo.findAll(pageable);
		if(agents.isEmpty()) {
			throw new ListIsEmptyException(HttpStatus.BAD_REQUEST, "Agents List Is Empty!!!");
		}
		return new ResponseEntity<>(agents,HttpStatus.OK) ;
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
			existingAgent.setEmail(updatedAgent.getEmail());
			existingAgent.setPhoneno(updatedAgent.getPhoneno());
			existingAgent.setQualification(updatedAgent.getQualification());
			
			User existingUser = userRepo.findById(existingAgent.getUser().getUserid()).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"User Not Found!!!"));
			if(existingUser != null) {
				existingUser.setUsername(updatedAgent.getUser().getUsername());
				if(updatedAgent.getUser().getPassword() != null) {
					existingUser.setPassword(passwordEncoder.encode(updatedAgent.getUser().getPassword()));
				}
				existingAgent.setUser(existingUser);
			}
		}else {
			throw new UserAPIException(HttpStatus.BAD_REQUEST, "Agent Not Found!!!");
		}
		return agentRepo.save(existingAgent);
	}
	
	public void activeAgentStatus(int agentId) {
		int newStatusId = 1;
		Agent agent = agentRepo.findById(agentId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Agent Not Found!!!"));
		if(agent != null) {
        	UserStatus newStatus = new UserStatus();
        	newStatus.setStatusid(newStatusId);
        	newStatus.setStatusname("ACTIVE");
        	agent.setUserStatus(newStatus);
        	agentRepo.save(agent);
        	
        }
    }

    public void inactiveAgentStatus(int agentId) {
    	int newStatusId = 2;
    	Agent agent = agentRepo.findById(agentId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Agent Not Found!!!"));
		if(agent != null) {
        	UserStatus newStatus = new UserStatus();
        	newStatus.setStatusid(newStatusId);
        	newStatus.setStatusname("INACTIVE");
        	agent.setUserStatus(newStatus);
        	agentRepo.save(agent);
        	
        }
    }

}
