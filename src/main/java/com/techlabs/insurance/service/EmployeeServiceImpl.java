package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.techlabs.insurance.entities.Employee;
import com.techlabs.insurance.entities.Role;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.exception.UserAPIException;
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
	public void deleteEmployee(int employeeId) {
		try {
			employeeRepo.deleteById(employeeId);
		}catch (UserAPIException e) {
			throw new UserAPIException(HttpStatus.BAD_REQUEST, "Employee Not Found");
		}
		
		
	}
	
	
}
