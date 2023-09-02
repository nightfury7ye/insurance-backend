package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.techlabs.insurance.entities.Employee;
import com.techlabs.insurance.entities.Role;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.repo.EmployeeRepo;
import com.techlabs.insurance.repo.RoleRepo;
import com.techlabs.insurance.repo.UserRepo;

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
	
	@Override
	public Employee saveEmployee(Employee employee) {
		User user = employee.getUser() ;
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Optional<Role> userRole= roleRepo.findById(3);
		List<Role> roles = new ArrayList<Role>();
		if(userRole.isPresent()) {
			roles.add(userRole.get());
		}		
		user.setRoles(roles);
		employee.setUser(user);
		return employeeRepo.save(employee);
	}

	@Override
	public Page<Employee> getAllEmployees(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return employeeRepo.findAll(pageable);
	}

	@Override
	public Employee updateEmployee(int employeeId, Employee updatedEmployee) {
		Employee existingEmployee = employeeRepo.findById(employeeId).orElse(null);
		if(existingEmployee != null) {
			existingEmployee.setFirstname(updatedEmployee.getFirstname());
			existingEmployee.setLastname(updatedEmployee.getLastname());
			
			User existingUser = userRepo.findById(existingEmployee.getUser().getUserid()).orElse(null);
			if(existingUser != null) {
				existingUser.setUsername(updatedEmployee.getUser().getUsername());
				existingUser.setPassword(passwordEncoder.encode(updatedEmployee.getUser().getPassword()));
				userRepo.save(existingUser);
			}
			
			return employeeRepo.save(existingEmployee);
		}
		return null;
	}

	@Override
	public void deleteEmployee(int employeeId) {
		employeeRepo.deleteById(employeeId);
		
	}
	
	
}
