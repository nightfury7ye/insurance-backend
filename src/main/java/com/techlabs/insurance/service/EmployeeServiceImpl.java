package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.techlabs.insurance.entities.Employee;
import com.techlabs.insurance.entities.Role;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.entities.UserStatus;
import com.techlabs.insurance.exception.ListIsEmptyException;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.repo.EmployeeRepo;
import com.techlabs.insurance.repo.RoleRepo;
import com.techlabs.insurance.repo.UserRepo;
import com.techlabs.insurance.repo.UserStatusRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000")
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	UserStatusRepo userStatusRepo;
	
	@Override
	public Employee saveEmployee(Employee employee, int statusId) {
		User user = employee.getUser() ;
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Optional<Role> userRole= roleRepo.findById(3);
		List<Role> roles = new ArrayList<Role>();
		if(userRole.isPresent()) {
			roles.add(userRole.get());
		}		
		user.setRoles(roles);
		employee.setUser(user);
		Optional<UserStatus> status = userStatusRepo.findById(statusId);
		if(status.isPresent()) {
			employee.setUserStatus(status.get());
		}else {
			employee.setUserStatus(userStatusRepo.findById(1).get());
		}
		return employeeRepo.save(employee);
	}

	@Override
	public ResponseEntity<Page<Employee>> getAllEmployees(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Employee> employees =employeeRepo.findAll(pageable);
		if(employees.isEmpty()) {
			throw new ListIsEmptyException(HttpStatus.BAD_REQUEST, "Employee List Is Empty!!!");
		}
		return new ResponseEntity<>(employees,HttpStatus.OK) ;
	}

	@Override
	public Employee updateEmployee(int employeeId, Employee updatedEmployee) {
		Employee existingEmployee = employeeRepo.findById(employeeId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Employee Not Found!!!"));
		if(existingEmployee != null) {
			existingEmployee.setFirstname(updatedEmployee.getFirstname());
			existingEmployee.setLastname(updatedEmployee.getLastname());
			
			User existingUser = userRepo.findById(existingEmployee.getUser().getUserid()).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"User Not Found!!!"));
			if(existingUser != null) {
				existingUser.setUsername(updatedEmployee.getUser().getUsername());
				if(updatedEmployee.getUser().getPassword() != null) {
					existingUser.setPassword(passwordEncoder.encode(updatedEmployee.getUser().getPassword()));
				}
				existingEmployee.setUser(existingUser);
			}
			
		}
		return employeeRepo.save(existingEmployee);
	}

	@Override
	public void inactiveEmployee(int employeeId) {
		Employee employee = employeeRepo.findById(employeeId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Employee Not Found!!!"));
        int newStatusId=2;
        
        if(employee != null) {
        	UserStatus newStatus = new UserStatus();
        	newStatus.setStatusid(newStatusId);
        	newStatus.setStatusname("INACTIVE");
        	employee.setUserStatus(newStatus);
        	employeeRepo.save(employee);
        	
        }
	}

	@Override
	public void activeEmployee(int employeeId) {
		Employee employee = employeeRepo.findById(employeeId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Employee Not Found!!!"));
        int newStatusId=1;
        
        if(employee != null) {
        	UserStatus newStatus = new UserStatus();
        	newStatus.setStatusid(newStatusId);
        	newStatus.setStatusname("ACTIVE");
        	employee.setUserStatus(newStatus);
        	employeeRepo.save(employee);
        	
        }
	}	
	
}
