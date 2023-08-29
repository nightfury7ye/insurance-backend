package com.techlabs.insurance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Policy;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.payload.RegisterDto;
import com.techlabs.insurance.repo.CustomerRepo;
import com.techlabs.insurance.repo.PolicyRepo;

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
	
}
