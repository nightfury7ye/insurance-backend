package com.techlabs.insurance.controller;

import java.util.List;

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
import com.techlabs.insurance.entities.Employee;
import com.techlabs.insurance.entities.InsurancePlan;
import com.techlabs.insurance.entities.InsuranceScheme;
import com.techlabs.insurance.service.CustomerService;
import com.techlabs.insurance.service.EmployeeService;
import com.techlabs.insurance.service.InsurancePlanService;
import com.techlabs.insurance.service.InsuranceSchemeService;

@RestController
@RequestMapping("/adminapp")
public class AdminController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private InsurancePlanService insurancePlanService;
	@Autowired
	private InsuranceSchemeService insuranceSchemeService;
	@Autowired
	private CustomerService customerService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save_employee")
	Employee saveEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get_all_employees")
	public Page<Employee> getAllEmployees(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return employeeService.getAllEmployees(page, size);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save_insuranceplan/{statusid}")
	public InsurancePlan saveInsurancePlan(@RequestBody InsurancePlan insurancePlan,@PathVariable(name="statusid") int statusid) {
		return insurancePlanService.saveInsurancePlan(insurancePlan, statusid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update_insuranceplan/{planid}/{statusid}")
	InsurancePlan updateInsurancePlan(@RequestBody InsurancePlan insurancePlanData,@PathVariable(name="planid") int planid,@PathVariable(name="statusid") int statusid) {
		return insurancePlanService.updateInsurancePlan(insurancePlanData, planid, statusid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete_insuranceplan/{planid}")
	String deleteInsurancePlan(@PathVariable(name="planid") int planid) {
		return insurancePlanService.deleteInsurancePlan(planid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get_scheme_by_planid/{planid}")
	List<InsuranceScheme> getInsuranceSchemeById(@PathVariable(name="planid") int planid){
		return insurancePlanService.getInsuranceSchemeById(planid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update_insurance_scheme/{schemeid}/{statusid}")
	InsuranceScheme updateInsuranceScheme(@RequestBody InsuranceScheme insuranceSchemeData,@PathVariable(name="schemeid") int schemeid,@PathVariable(name="statusid") int statusid) {
		return insuranceSchemeService.updateInsuranceScheme(insuranceSchemeData, schemeid, statusid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete_insurance_scheme/{schemeid}")
	public String deleteInsuranceScheme(@PathVariable(name="schemeid") int schemeid) {
		return insuranceSchemeService.deleteInsuranceScheme(schemeid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/save_insurance_scheme/{planid}/{statusid}")
	public InsuranceScheme saveInsuranceScheme(@RequestBody InsuranceScheme insuranceScheme,@PathVariable(name="planid") int planid,@PathVariable(name="statusid") int statusid) {
		return insuranceSchemeService.saveInsuranceScheme(insuranceScheme, planid, statusid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get_insurance_plans")
	public List<InsurancePlan> getInsurancePlans() {
		return insurancePlanService.getInsurancePlans();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getallcustomers")
	public Page<Customer> getAllCustomers(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return customerService.getAllCustomers(page, size);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete_employee/{employeeid}")
	public void deleteCustomer(@PathVariable(name="employeeid")int employeeId) {
		employeeService.deleteEmployee(employeeId);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/enablecustomer/{customerId}")
    public ResponseEntity<String> enableCustomer(@PathVariable(name="customerId") int customerId) {
        customerService.enableCustomerStatus(customerId);
        return ResponseEntity.ok("Customer status enabled");
    }

	@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/disablecustomer/{customerId}")
    public ResponseEntity<String> disableCustomer(@PathVariable(name="customerId") int customerId) {
        customerService.disableCustomerStatus(customerId);
        return ResponseEntity.ok("Customer status disabled");
    }
}
