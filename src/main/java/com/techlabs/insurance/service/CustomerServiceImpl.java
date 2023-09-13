package com.techlabs.insurance.service;

import java.util.NoSuchElementException;
import java.util.Optional;

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
import com.techlabs.insurance.exception.ListIsEmptyException;
import com.techlabs.insurance.exception.UserAPIException;
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
	public Customer registerCustomer(Customer customer) {
		RegisterDto registerDto = new RegisterDto();
		registerDto.setUsername(customer.getUser().getUsername());
		registerDto.setPassword(customer.getUser().getPassword());
		User user = authService.register(registerDto, 1);
		customer.setUser(user);
		customer.setUserStatus(userStatusRepo.findById(1).get());
		return customerRepo.save(customer);
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
	public Customer updateCustomer(int customerId, Customer customer) {
		Customer existingCustomer = customerRepo.findById(customerId)
	            .orElseThrow(() -> new NoSuchElementException("Customer not found"));

	    existingCustomer.setFirstname(customer.getFirstname());
	    existingCustomer.setLastname(customer.getLastname());
	    existingCustomer.setEmail(customer.getEmail());
	    existingCustomer.setPhoneno(customer.getPhoneno());
	    existingCustomer.setDOB(customer.getDOB());

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

	    return customerRepo.save(existingCustomer);
	}

	@Override
	public Customer registerCustomerByAgent(Customer customer, int agentid) {
		RegisterDto registerDto = new RegisterDto();
		registerDto.setUsername(customer.getUser().getUsername());
		registerDto.setPassword(customer.getUser().getPassword());
		User user = authService.register(registerDto, 1);
		customer.setUser(user);
		Agent agent = agentRepo.findById(agentid).orElseThrow();
		customer.setAgent(agent);
		customer.setUserStatus(userStatusRepo.findById(1).get());
		return customerRepo.save(customer);
	}

	@Override
	public void updateDocumentStatus(int customerId) {
		Customer customer = customerRepo.findById(customerId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Customer Not Found!!!"));
		customer.setDocumentStatus("Approved");
		customerRepo.save(customer);
	}
	
}
