package com.techlabs.insurance.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entities.Customer;
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
	public Customer getCustomerByUsername(String username) {
		return customerRepo.findByUserUsername(username);
	}
	
}
