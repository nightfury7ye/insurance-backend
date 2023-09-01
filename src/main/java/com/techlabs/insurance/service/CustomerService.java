package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;

import com.techlabs.insurance.entities.Customer;

public interface CustomerService {
	Customer registerCustomer(Customer customer);
	Page<Customer> getAllCustomers(int page, int size);
	Customer getCustomerById(int customerId);
	void deleteCustomer(int customerId);
	Page<Customer> getAllDisabledCustomers(int page, int size);
	Customer updateCustomerStatus(int customerId, int newStatusId);
}
