package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entities.Customer;

public interface CustomerService {
	public Customer registerCustomer(Customer customer);
	public Page<Customer> getAllCustomers(int page, int size);
	public Customer getCustomerById(int customerId);
	public void deleteCustomer(int customerId);
	public Page<Customer> getAllDisabledCustomers(int page, int size);
	public Customer updateCustomerStatus(int customerId, int newStatusId);
	public void enableCustomerStatus(int customerId);
	public void disableCustomerStatus(int customerId);

	public Customer getCustomerByUsername(String username);
}

