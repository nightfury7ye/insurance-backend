package com.techlabs.insurance.service;

import java.sql.Date;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.entities.UserStatus;
import com.techlabs.insurance.exception.InvalidEmailException;
import com.techlabs.insurance.exception.InvalidFirstnameException;
import com.techlabs.insurance.exception.InvalidLastnameException;
import com.techlabs.insurance.exception.InvalidPasswordException;
import com.techlabs.insurance.exception.InvalidPhonenoException;
import com.techlabs.insurance.exception.ListIsEmptyException;
import com.techlabs.insurance.exception.RegistrationFailedException;
import com.techlabs.insurance.exception.StatusNotFoundException;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.exception.UsernameAlreadyExistsException;
import com.techlabs.insurance.payload.RegisterDto;
import com.techlabs.insurance.repo.AgentRepo;
import com.techlabs.insurance.repo.CustomerRepo;
import com.techlabs.insurance.repo.PolicyRepo;
import com.techlabs.insurance.repo.UserRepo;
import com.techlabs.insurance.repo.UserStatusRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private AuthService authService;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private PolicyRepo policyRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserStatusRepo userStatusRepo;
	@Autowired
	private AgentRepo agentRepo;
	
	@Override
	public ResponseEntity<Customer> registerCustomer(Customer customer) {
	    if (customer == null || customer.getUser() == null || customer.getUser().getUsername() == null || customer.getUser().getPassword() == null) {
	        throw new IllegalArgumentException("Customer data is incomplete");
	    }

	    String username = customer.getUser().getUsername();
	    String password = customer.getUser().getPassword();
	    
	    String firstname = customer.getFirstname();
	    String lastname = customer.getLastname();
	    String email = customer.getEmail();
	    long phoneno = customer.getPhoneno();
	    Date dob = customer.getDOB();

	    if (userRepo.existsByUsername(username)) {
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

	    RegisterDto registerDto = new RegisterDto();
	    registerDto.setUsername(username);
	    registerDto.setPassword(password);

	    User user = authService.register(registerDto, 1);

	    if (user == null) {
	        throw new RegistrationFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed");
	    }

	    customer.setUser(user);

	    UserStatus userStatus = userStatusRepo.findById(1).orElse(null);

	    if (userStatus == null) {
	        throw new StatusNotFoundException( HttpStatus.NOT_FOUND,"User status not found");
	    }

	    customer.setUserStatus(userStatus);

	    customerRepo.save(customer);

	    return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Page<Customer>> getAllCustomers(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Customer> customers =customerRepo.findAll(pageable);
		if(customers.isEmpty()) {
			throw new ListIsEmptyException(HttpStatus.BAD_REQUEST, "Employee List Is Empty!!!");
		}
		return new ResponseEntity<>(customers,HttpStatus.OK) ;
	}
	
	@Override
	public ResponseEntity<Page<Customer>> getCustomersByAgentid(int agentid, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Customer> customers =customerRepo.findByAgentAgentid(agentid,pageable);
		if(customers.isEmpty()) {
			throw new ListIsEmptyException(HttpStatus.BAD_REQUEST, "Employee List Is Empty!!!");
		}
		return new ResponseEntity<>(customers,HttpStatus.OK) ;
	}

	@Override
	public Customer getCustomerById(int customerId) {
		Customer customerById=customerRepo.findById(customerId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Customer Not Found!!!"));
		Customer customer = customerById;
		return customer;
	}

	@Override
	public void deleteCustomer(int customerId) {
		try {
			customerRepo.deleteById(customerId);
		}catch (Exception e) {
			throw new UserAPIException(HttpStatus.BAD_REQUEST, "Customer Not Found");
		}
		
	}

//	@Override
//	public Page<Customer> getAllDisabledCustomers(int page, int size) {
//		int disabledStatusId = 2;
//		Pageable pageable = PageRequest.of(page, size);
//
//		return customerRepo.findByUserStatusStatusid(disabledStatusId, pageable);
//		//return customerRepo.findById(disabledStatusId);
//	}
	
	@Override
	public Page<Customer> getAllDisabledCustomers(int page, int size) {
		String docStatus = "Pending";
		Pageable pageable = PageRequest.of(page, size);

		return customerRepo.findByDocumentStatus(docStatus, pageable);
	}

	@Override
	public Customer updateCustomerStatus(int customerId, int newStatusId) {
		Customer customer = customerRepo.findById(customerId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Customer Not Found!!!"));
		Optional<UserStatus> status = userStatusRepo.findById(newStatusId);
		if(status.isPresent()){
			customer.setUserStatus(status.get());
		}
		return customerRepo.save(customer);
	}
	
	public void activeCustomerStatus(int customerId) {
		int newStatusId = 1;
		Customer customer = customerRepo.findById(customerId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Customer Not Found!!!"));
		if(customer != null) {
        	UserStatus newStatus = new UserStatus();
        	newStatus.setStatusid(newStatusId);
        	newStatus.setStatusname("ACTIVE");
        	customer.setUserStatus(newStatus);
        	customerRepo.save(customer);
        	
        }
    }

    public void inactiveCustomerStatus(int customerId) {
    	int newStatusId = 2;
		Customer customer = customerRepo.findById(customerId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Customer Not Found!!!"));
		if(customer != null) {
        	UserStatus newStatus = new UserStatus();
        	newStatus.setStatusid(newStatusId);
        	newStatus.setStatusname("INACTIVE");
        	customer.setUserStatus(newStatus);
        	customerRepo.save(customer);
        	
        }
    }
	
	@Override
	public Customer getCustomerByUsername(String username) {
		return customerRepo.findByUserUsername(username);
	}

	@Override
	public ResponseEntity<Customer> updateCustomer(int customerId, Customer customer) {
		Customer existingCustomer = customerRepo.findById(customerId)
	            .orElseThrow(() -> new UserAPIException(HttpStatus.NOT_FOUND,"Customer not found"));
		
		    String username = customer.getUser().getUsername();
		    String password = customer.getUser().getPassword();
		    
		    String firstname = customer.getFirstname();
		    String lastname = customer.getLastname();
		    String email = customer.getEmail();
		    long phoneno = customer.getPhoneno();
		    Date dob = customer.getDOB();

		    if (userRepo.existsByUsername(username)) {
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

	    existingCustomer.setFirstname(firstname);
	    existingCustomer.setLastname(lastname);
	    existingCustomer.setEmail(email);
	    existingCustomer.setPhoneno(phoneno);
	    existingCustomer.setDOB(dob);

	    existingCustomer.getAddress().setAddress(customer.getAddress().getAddress());
	    existingCustomer.getAddress().setCity(customer.getAddress().getCity());
	    existingCustomer.getAddress().setState(customer.getAddress().getState());
	    existingCustomer.getAddress().setPincode(customer.getAddress().getPincode());

	    existingCustomer.getNominee().setNomineename(customer.getNominee().getNomineename());
	    existingCustomer.getNominee().setNomineeRelation(customer.getNominee().getNomineeRelation());

	    User existingUser = userRepo.findById(existingCustomer.getUser().getUserid()).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"User Not Found!!!"));
		if(existingUser != null) {
			existingUser.setUsername(customer.getUser().getUsername());
			if(customer.getUser().getPassword() != null) {
				existingUser.setPassword(passwordEncoder.encode(customer.getUser().getPassword()));
			}
			existingCustomer.setUser(existingUser);
		}

	    customerRepo.save(existingCustomer);
	    return new ResponseEntity<>(existingCustomer, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Customer> registerCustomerByAgent(Customer customer, int agentid) {
		if (customer == null || customer.getUser() == null || customer.getUser().getUsername() == null || customer.getUser().getPassword() == null) {
		        throw new IllegalArgumentException("Customer data is incomplete");
		}
		

	    String username = customer.getUser().getUsername();
	    String password = customer.getUser().getPassword();
	    
	    String firstname = customer.getFirstname();
	    String lastname = customer.getLastname();
	    String email = customer.getEmail();
	    long phoneno = customer.getPhoneno();
	    Date dob = customer.getDOB();

	    if (userRepo.existsByUsername(username)) {
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
		 
		RegisterDto registerDto = new RegisterDto();
		registerDto.setUsername(username);
		registerDto.setPassword(customer.getUser().getPassword());
		
		User user = authService.register(registerDto, 1);

	    if (user == null) {
	        throw new RegistrationFailedException(HttpStatus.INTERNAL_SERVER_ERROR, "User registration failed");
	    }

	    customer.setUser(user);
	    
		Agent agent = agentRepo.findById(agentid).orElseThrow(()-> new UserAPIException(HttpStatus.NOT_FOUND,"Agent Not Found!!!"));
		customer.setAgent(agent);
		
		UserStatus userStatus = userStatusRepo.findById(1).orElse(null);

	    if (userStatus == null) {
	        throw new StatusNotFoundException( HttpStatus.NOT_FOUND,"User status not found");
	    }

	    customer.setUserStatus(userStatus);
		customerRepo.save(customer);
		return new ResponseEntity<>(customer,HttpStatus.OK) ;
	}

	@Override
	public void updateDocumentStatus(int customerId) {
		Customer customer = customerRepo.findById(customerId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Customer Not Found!!!"));
		customer.setDocumentStatus("Approved");
		customerRepo.save(customer);
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
