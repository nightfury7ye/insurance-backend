package com.techlabs.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.service.CustomerService;

@RestController
@RequestMapping("/insurance-app")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/users/customer")
	public Customer registerCustomer(@RequestBody Customer customer) {
		return customerService.registerCustomer(customer);
	}
	
	@GetMapping("/users/customer")
	public Customer getCustomerByUsername(@RequestParam(name="username") String username) {
		return customerService.getCustomerByUsername(username);
	}
	
	//@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/users/customer/{customerid}")
	public Customer getCustomerById(@PathVariable(name="customerid")int customerId) {
		return customerService.getCustomerById(customerId);
	}
	
	@GetMapping("/users/customers")
	public ResponseEntity<Page<Customer>> getAllCustomers(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return customerService.getAllCustomers(page, size);
	}
	
	@PreAuthorize("hasRole('AGENT')")
	@GetMapping("/users/customers/{agentid}")
	public ResponseEntity<Page<Customer>> getCustomersByAgentid(@PathVariable(name="agentid") int agentid,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		System.out.println("Inside getCustomersByAgentid");
		return customerService.getCustomersByAgentid(agentid, page, size);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@DeleteMapping("/users/customer/{customerid}")
	public void deleteCustomer(@PathVariable(name="customerid")int customerId) {
		customerService.deleteCustomer(customerId);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/disabled_customers")
	public Page<Customer> getAllDisabledCustomers(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return customerService.getAllDisabledCustomers(page, size);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/users/customer/{customerid}/status/{statusid}")
	public Customer updateCustomerStatus(@PathVariable(name="customerid")int customerId, @PathVariable(name="statusid")int statusId) {
		return customerService.updateCustomerStatus(customerId, statusId);
	}
	
	@PutMapping("/users/customer/{customerid}")
	public Customer updateCustomer(@PathVariable(name="customerid")int customerId, @RequestBody Customer customer) {
		return customerService.updateCustomer(customerId, customer);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/activecustomer/{customerId}")
    public ResponseEntity<String> activeCustomer(@PathVariable(name="customerId") int customerId) {
        customerService.activeCustomerStatus(customerId);
        return ResponseEntity.ok("Customer status enabled");
    }

	@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/inactivecustomer/{customerId}")
    public ResponseEntity<String> inactiveCustomer(@PathVariable(name="customerId") int customerId) {
        customerService.inactiveCustomerStatus(customerId);
        return ResponseEntity.ok("Customer status disabled");
    }
	
}
