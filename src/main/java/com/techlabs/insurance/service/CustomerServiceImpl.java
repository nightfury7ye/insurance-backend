package com.techlabs.insurance.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Employee;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.entities.User_status;
import com.techlabs.insurance.exception.ListIsEmptyException;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.payload.RegisterDto;
import com.techlabs.insurance.repo.CustomerRepo;
import com.techlabs.insurance.repo.PolicyRepo;
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
	private UserStatusRepo userStatusRepo;
	
	@Override
	public Customer registerCustomer(Customer customer) {
		RegisterDto registerDto = new RegisterDto();
		registerDto.setUsername(customer.getUser().getUsername());
		registerDto.setPassword(customer.getUser().getPassword());
		User user = authService.register(registerDto, 1);
		customer.setUser(user);
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

	@Override
	public Page<Customer> getAllDisabledCustomers(int page, int size) {
		int disabledStatusId = 2;
		Pageable pageable = PageRequest.of(page, size);

		return customerRepo.findByUserstatusStatusid(disabledStatusId, pageable);
		//return customerRepo.findById(disabledStatusId);
	}

	@Override
	public Customer updateCustomerStatus(int customerId, int newStatusId) {
		Customer customer = customerRepo.findById(customerId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Customer Not Found!!!"));
		Optional<User_status> status = userStatusRepo.findById(newStatusId);
		if(status.isPresent()){
			customer.setUserstatus(status.get());
		}
		return customerRepo.save(customer);
	}
	
	public void enableCustomerStatus(int customerId) {
		int enabledStatus = 1;
		Customer customer = customerRepo.findById(customerId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Customer Not Found!!!"));
		User_status status = customer.getUserstatus();
        if (status.getStatusid()==2) {
        	status.setStatusid(enabledStatus);
        	customer.setUserstatus(status);
    		customerRepo.save(customer);
        }
    }

    public void disableCustomerStatus(int customerId) {
    	int disabledStatus = 2;
		Customer customer = customerRepo.findById(customerId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Customer Not Found!!!"));
		User_status status = customer.getUserstatus();
        if (status.getStatusid()==1) {
        	status.setStatusid(disabledStatus);
        	customer.setUserstatus(status);
    		customerRepo.save(customer);
        }
    }
	
	@Override
	public Customer getCustomerByUsername(String username) {
		return customerRepo.findByUserUsername(username);
	}
	
}
