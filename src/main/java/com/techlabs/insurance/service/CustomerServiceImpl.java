package com.techlabs.insurance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Policy;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.entities.User_status;
import com.techlabs.insurance.payload.RegisterDto;
import com.techlabs.insurance.repo.CustomerRepo;
import com.techlabs.insurance.repo.PolicyRepo;

import io.jsonwebtoken.lang.Collections;
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
	public Page<Customer> getAllCustomers(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return customerRepo.findAll(pageable);
	}

	@Override
	public Customer getCustomerById(int customerId) {
		Optional<Customer> customerById=customerRepo.findById(customerId);
		Customer customer = customerById.get();
		return customer;
	}

	@Override
	public void deleteCustomer(int customerId) {
		customerRepo.deleteById(customerId);
	}

	@Override
	public Page<Customer> getAllDisabledCustomers(int page, int size) {
		int disabledStatusId = 2;
		Pageable pageable = PageRequest.of(page, size);
		return customerRepo.findByStatusid(disabledStatusId, pageable);
	}

	@Override
	public Customer updateCustomerStatus(int customerId, int newStatusId) {
		Customer customer = customerRepo.findById(customerId).orElse(null);
		User_status status = customer.getUser_status();
		status.setStatusid(newStatusId);
		customer.setUser_status(status);
		return customerRepo.save(customer);
	}
	
}
