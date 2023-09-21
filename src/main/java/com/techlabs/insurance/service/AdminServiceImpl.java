package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.techlabs.insurance.entities.Admin;
import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Role;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.exception.InvalidEmailException;
import com.techlabs.insurance.exception.InvalidFirstnameException;
import com.techlabs.insurance.exception.InvalidLastnameException;
import com.techlabs.insurance.exception.InvalidPasswordException;
import com.techlabs.insurance.exception.InvalidPhonenoException;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.exception.UsernameAlreadyExistsException;
import com.techlabs.insurance.repo.AdminRepo;
import com.techlabs.insurance.repo.RoleRepo;
import com.techlabs.insurance.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000")
public class AdminServiceImpl implements AdminService{
	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Admin getAdminById(int adminId) {
		return adminRepo.findById(adminId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Admin not found with id"+adminId));
	}

	@Transactional
	@Override
	public ResponseEntity<Admin> updateAdminProfile(int adminId, Admin updatedAdmin) {
		Admin existingAdmin = adminRepo.findById(adminId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Admin not found with id"+adminId));
		
		String username = updatedAdmin.getUser().getUsername();
		String password = updatedAdmin.getUser().getPassword();
		    
		String firstname = updatedAdmin.getFirstname();
		String lastname = updatedAdmin.getLastname();
		String email = updatedAdmin.getEmail();
		long phoneno = updatedAdmin.getPhoneno();
		User user = updatedAdmin.getUser();
		
		if(userRepo.existsByUsername(username)) {
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
		
		if(existingAdmin != null) {
			existingAdmin.setFirstname(firstname);
			existingAdmin.setLastname(lastname);
			existingAdmin.setEmail(email);
			existingAdmin.setPhoneno(phoneno);
			
			User existingUser = userRepo.findById(existingAdmin.getUser().getUserid()).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"User Not Found!!!"));
			if(existingUser != null) {
				existingUser.setUsername(username);
				if(password != null) {
					existingUser.setPassword(passwordEncoder.encode(password));
				}
				existingAdmin.setUser(existingUser);
			}
		}
		adminRepo.save(existingAdmin);
		return new ResponseEntity<>(existingAdmin,HttpStatus.OK) ;
	}

	@Override
	public ResponseEntity<Admin> saveAdmin(Admin admin) {
		String username = admin.getUser().getUsername();
		String password = admin.getUser().getPassword();
		    
		String firstname = admin.getFirstname();
		String lastname = admin.getLastname();
		String email = admin.getEmail();
		long phoneno = admin.getPhoneno();
		
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
		
		User user = new User();
		
		if(userRepo.existsByUsername(username)) {
			throw new UsernameAlreadyExistsException("Username already exists!!!", HttpStatus.BAD_REQUEST);
		}
		
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		
		Optional<Role> userRole= roleRepo.findById(4);
		List<Role> roles = new ArrayList<Role>();
		if(userRole.isPresent()) {
			roles.add(userRole.get());
		}		
		user.setRoles(roles);
		
		Admin newAdmin = new Admin();
		newAdmin.setFirstname(firstname);
		newAdmin.setLastname(lastname);
		newAdmin.setEmail(email);
		newAdmin.setPhoneno(phoneno);
		newAdmin.setUser(user);
		
		adminRepo.save(newAdmin);
		return new ResponseEntity<>(newAdmin,HttpStatus.OK) ;
	}

	@Override
	public Admin getAdminByUsername(String username) {
		return adminRepo.findByUserUsername(username);
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
