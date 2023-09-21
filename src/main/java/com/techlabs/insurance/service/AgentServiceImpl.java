package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.techlabs.insurance.exception.InvalidEmailException;
import com.techlabs.insurance.exception.InvalidFirstnameException;
import com.techlabs.insurance.exception.InvalidLastnameException;
import com.techlabs.insurance.exception.InvalidPasswordException;
import com.techlabs.insurance.exception.InvalidPhonenoException;
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
		String username = agent.getUser().getUsername();
		String password = agent.getUser().getPassword();
		    
		String firstname = agent.getFirstname();
		String lastname = agent.getLastname();
		String email = agent.getEmail();
		long phoneno = agent.getPhoneno();
		User user = agent.getUser();
		
		if(userRepo.existsByUsername(username)) {
			throw new UsernameAlreadyExistsException("Username already exists!!!", HttpStatus.BAD_REQUEST);
		}
		
		if (!isValidPassword(password)) {
	        throw new InvalidPasswordException(HttpStatus.BAD_REQUEST, "Invalid password format");
	    }
	    
	    if(!isValidFirstname(firstname)) {
	    	throw new InvalidFirstnameException(HttpStatus.BAD_REQUEST, "Invalid firstname format");
	    }
	    
	    if(!isValidLastname(lastname)) {
	    	throw new InvalidLastnameException(HttpStatus.BAD_REQUEST, "Invalid lastname format");
	    }
	    
	    if(!isValidEmail(email)) {
	    	throw new InvalidEmailException(HttpStatus.BAD_REQUEST, "Invalid email format");
	    }
	    
	    if(!isValidPhoneno(phoneno)) {
	    	throw new InvalidPhonenoException(HttpStatus.BAD_REQUEST, "Invalid phoneno format");
	    }
		
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		
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
	public ResponseEntity<Agent> updateAgentStatus(int agentId, int statusId) {
		Agent agent = agentRepo.findById(agentId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Agent Not Found!!!"));
		
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
	public ResponseEntity<Agent> updateAgentProfile(String username, Agent updatedAgent) {
		String username2 = updatedAgent.getUser().getUsername();
		String password = updatedAgent.getUser().getPassword();
		    
		String firstname = updatedAgent.getFirstname();
		String lastname = updatedAgent.getLastname();
		String email = updatedAgent.getEmail();
		long phoneno = updatedAgent.getPhoneno();
		User user = updatedAgent.getUser();
		
		if(userRepo.existsByUsername(username)) {
			throw new UsernameAlreadyExistsException("Username already exists!!!", HttpStatus.BAD_REQUEST);
		}
		
		if (!isValidPassword(password)) {
	        throw new InvalidPasswordException(HttpStatus.BAD_REQUEST, "Invalid password format");
	    }
	    
	    if(!isValidFirstname(firstname)) {
	    	throw new InvalidFirstnameException(HttpStatus.BAD_REQUEST, "Invalid firstname format");
	    }
	    
	    if(!isValidLastname(lastname)) {
	    	throw new InvalidLastnameException(HttpStatus.BAD_REQUEST, "Invalid lastname format");
	    }
	    
	    if(!isValidEmail(email)) {
	    	throw new InvalidEmailException(HttpStatus.BAD_REQUEST, "Invalid email format");
	    }
	    
	    if(!isValidPhoneno(phoneno)) {
	    	throw new InvalidPhonenoException(HttpStatus.BAD_REQUEST, "Invalid phoneno format");
	    }
		
		Agent existingAgent = agentRepo.findByUserUsername(username);
		if(existingAgent != null) {
			existingAgent.setFirstname(firstname);
			existingAgent.setLastname(lastname);
			existingAgent.setEmail(email);
			existingAgent.setPhoneno(phoneno);
			existingAgent.setQualification(updatedAgent.getQualification());
			
			User existingUser = userRepo.findById(existingAgent.getUser().getUserid()).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"User Not Found!!!"));
			if(existingUser != null) {
				existingUser.setUsername(username2);
				if(password != null) {
					existingUser.setPassword(passwordEncoder.encode(password));
				}
				existingAgent.setUser(existingUser);
			}
		}else {
			throw new UserAPIException(HttpStatus.BAD_REQUEST, "Agent Not Found!!!");
		}
		agentRepo.save(existingAgent);
		return new ResponseEntity<>(existingAgent,HttpStatus.OK) ;
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
    
    private boolean isValidPassword(String password) {
		 String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&!])[A-Za-z\\d@#$%^&!]{8,}$";
	     Pattern pattern = Pattern.compile(passwordPattern);
	     Matcher matcher = pattern.matcher(password);
	     return matcher.matches();
	}
	

	private boolean isValidFirstname(String firstname) {
		String regex = "^[A-Za-z][A-Za-z\\s]*$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(firstname);
      return matcher.matches();
	}
	

	private boolean isValidLastname(String lastname) {
		String regex = "^[A-Za-z][A-Za-z\\s]*$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(lastname);
      return matcher.matches();
	}
	
	private boolean isValidEmail(String email) {
		String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";
      Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(email);
      return matcher.matches();
	}
	

	private boolean isValidPhoneno(long phoneno) {
		String regex = "^[0-9]{10}$";
		String phonenoString = String.valueOf(phoneno);
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(phonenoString);
      return matcher.matches();
	}


}
