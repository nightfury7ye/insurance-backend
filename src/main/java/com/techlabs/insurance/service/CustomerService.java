package com.techlabs.insurance.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Policy;

public interface CustomerService {
	public Customer registerCustomer(Customer customer);
	public Customer registerCustomerByAgent(Customer customer, int agentid);
	public ResponseEntity<Page<Customer>> getAllCustomers(int page, int size);
	public ResponseEntity<Page<Customer>> getCustomersByAgentid(int agentid, int page, int size);
	public Customer getCustomerById(int customerId);
	public void deleteCustomer(int customerId);
	public Page<Customer> getAllDisabledCustomers(int page, int size);
	public Customer updateCustomerStatus(int customerId, int newStatusId);
	public Customer updateCustomer(int customerId, Customer customer);
	public void activeCustomerStatus(int customerId);
	public void inactiveCustomerStatus(int customerId);
	public void updateDocumentStatus(int customerId);
	public Customer getCustomerByUsername(String username);
}

