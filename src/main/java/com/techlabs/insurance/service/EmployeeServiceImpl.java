package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.techlabs.insurance.exception.InvalidEmailException;
import com.techlabs.insurance.exception.InvalidFirstnameException;
import com.techlabs.insurance.exception.InvalidLastnameException;
import com.techlabs.insurance.exception.InvalidPasswordException;
import com.techlabs.insurance.exception.InvalidPhonenoException;
import com.techlabs.insurance.exception.ListIsEmptyException;
import com.techlabs.insurance.exception.StatusNotFoundException;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.exception.UsernameAlreadyExistsException;
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
	public ResponseEntity<Employee> saveEmployee(Employee employee, int statusId) {
		if (employee == null || employee.getUser() == null || employee.getUser().getUsername() == null || employee.getUser().getPassword() == null) {
		    throw new IllegalArgumentException("Employee data is incomplete");
		}

		String username = employee.getUser().getUsername();
		String password = employee.getUser().getPassword();
		    
		String firstname = employee.getFirstname();
		String lastname = employee.getLastname();
		String email = employee.getEmail();
		long phoneno = employee.getPhoneno();
		
	    User user = employee.getUser();

	    if (userRepo.existsByUsername(username)) {
	        throw new UsernameAlreadyExistsException("Username already exists!!!", HttpStatus.BAD_REQUEST);
	    }
	    
	    if (!isValidPassword(password)) {
	        throw new InvalidPasswordException(HttpStatus.BAD_REQUEST, "Invalid password format");
	    }
	    
	    if(!isValidFirstname(firstname)) {
	    	throw new InvalidFirstnameException(HttpStatus.BAD_REQUEST, "Invalid firstname format");
	    }
	    
	    if(!isValidLastname(lastname)) {
	    	throw new InvalidLastnameException(HttpStatus.BAD_REQUEST, "Invalid lastname format");
	    }
	    
	    if(!isValidEmail(email)) {
	    	throw new InvalidEmailException(HttpStatus.BAD_REQUEST, "Invalid email format");
	    }
	    
	    if(!isValidPhoneno(phoneno)) {
	    	throw new InvalidPhonenoException(HttpStatus.BAD_REQUEST, "Invalid phoneno format");
	    }

	    user.setPassword(passwordEncoder.encode(user.getPassword()));

	    Optional<Role> userRole = roleRepo.findById(3);

	    List<Role> roles = new ArrayList<>();
	    userRole.ifPresent(roles::add);
	    user.setRoles(roles);

	    employee.setUser(user);

	    Optional<UserStatus> status = userStatusRepo.findById(statusId);

	    if (status.isPresent()) {
	        employee.setUserStatus(status.get());
	    } else {
	        UserStatus defaultStatus = userStatusRepo.findById(1).orElseThrow(() ->
	            new StatusNotFoundException(HttpStatus.NOT_FOUND, "Default user status not found"));
	        employee.setUserStatus(defaultStatus);
	    }

	    employeeRepo.save(employee);

	    return new ResponseEntity<>(employee, HttpStatus.OK);
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
	public ResponseEntity<Employee> updateEmployee(int employeeId, Employee updatedEmployee) {
		Employee existingEmployee = employeeRepo.findById(employeeId).orElseThrow(()-> new UserAPIException(HttpStatus.NOT_FOUND,"Employee Not Found!!!"));

		String username = updatedEmployee.getUser().getUsername();
		String password = updatedEmployee.getUser().getPassword();
		    
		String firstname = updatedEmployee.getFirstname();
		String lastname = updatedEmployee.getLastname();
		String email = updatedEmployee.getEmail();
		long phoneno = updatedEmployee.getPhoneno();
		
		if (userRepo.existsByUsername(username)) {
	        throw new UsernameAlreadyExistsException("Username already exists!!!", HttpStatus.BAD_REQUEST);
	    }
	    
	    if (!isValidPassword(password)) {
	        throw new InvalidPasswordException(HttpStatus.BAD_REQUEST, "Invalid password format");
	    }
	    
	    if(!isValidFirstname(firstname)) {
	    	throw new InvalidFirstnameException(HttpStatus.BAD_REQUEST, "Invalid firstname format");
	    }
	    
	    if(!isValidLastname(lastname)) {
	    	throw new InvalidLastnameException(HttpStatus.BAD_REQUEST, "Invalid lastname format");
	    }
	    
	    if(!isValidEmail(email)) {
	    	throw new InvalidEmailException(HttpStatus.BAD_REQUEST, "Invalid email format");
	    }
	    
	    if(!isValidPhoneno(phoneno)) {
	    	throw new InvalidPhonenoException(HttpStatus.BAD_REQUEST, "Invalid phoneno format");
	    }
		
		if(existingEmployee != null) {
			existingEmployee.setFirstname(firstname);
			existingEmployee.setLastname(lastname);
			existingEmployee.setEmail(email);
			existingEmployee.setPhoneno(phoneno);
			
			User existingUser = userRepo.findById(existingEmployee.getUser().getUserid()).orElseThrow(()-> new UserAPIException(HttpStatus.NOT_FOUND,"User Not Found!!!"));
			if(existingUser != null) {
				existingUser.setUsername(username);
				if(password != null) {
					existingUser.setPassword(passwordEncoder.encode(password));
				}
				existingEmployee.setUser(existingUser);
			}
			
		}
		employeeRepo.save(existingEmployee);
		return new ResponseEntity<>(existingEmployee,HttpStatus.OK) ;
	}
	

	@Override
	public Employee getEmployeeById(int employeeId) {
		return employeeRepo.findById(employeeId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Employee Not Found!!!"));
	}	
	
	

	@Override
	public void inactiveEmployee(int employeeId) {
		Employee employee = employeeRepo.findById(employeeId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Employee Not Found!!!"));
        int newStatusId=2;
        System.out.println("inside inactiveEmployee");
        if(employee != null) {
        	UserStatus newStatus = new UserStatus();
        	newStatus.setStatusid(newStatusId);
        	newStatus.setStatusname("inactive");
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
        	newStatus.setStatusname("active");
        	employee.setUserStatus(newStatus);
        	employeeRepo.save(employee);
        	
        }
	}

	@Override
	public Employee getEmployeeByUsername(String username) {
		return employeeRepo.findByUserUsername(username);
	}
	
	private boolean isValidPassword(String password) {
		 String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&!])[A-Za-z\\d@#$%^&!]{8,}$";
	     Pattern pattern = Pattern.compile(passwordPattern);
	     Matcher matcher = pattern.matcher(password);
	     return matcher.matches();
	}
	

	private boolean isValidFirstname(String firstname) {
		String regex = "^[A-Za-z][A-Za-z\\s]*$";
       Pattern pattern = Pattern.compile(regex);
       Matcher matcher = pattern.matcher(firstname);
       return matcher.matches();
	}
	

	private boolean isValidLastname(String lastname) {
		String regex = "^[A-Za-z][A-Za-z\\s]*$";
       Pattern pattern = Pattern.compile(regex);
       Matcher matcher = pattern.matcher(lastname);
       return matcher.matches();
	}
	
	private boolean isValidEmail(String email) {
		String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";
       Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
       Matcher matcher = pattern.matcher(email);
       return matcher.matches();
	}
	

	private boolean isValidPhoneno(long phoneno) {
		String regex = "^[0-9]{10}$";
		String phonenoString = String.valueOf(phoneno);
       Pattern pattern = Pattern.compile(regex);
       Matcher matcher = pattern.matcher(phonenoString);
       return matcher.matches();
	}

}
