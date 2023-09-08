package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.techlabs.insurance.entities.Admin;
import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Role;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.exception.UserAPIException;
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

	@Override
	public Admin updateAdminProfile(int adminId, Admin updatedAdmin) {
		Admin existingAdmin = adminRepo.findById(adminId).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Admin not found with id"+adminId));
		if(existingAdmin != null) {
			existingAdmin.setFirstname(updatedAdmin.getFirstname());
			existingAdmin.setLastname(updatedAdmin.getLastname());
			
			User existingUser = userRepo.findById(existingAdmin.getUser().getUserid()).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"User Not Found!!!"));
			if(existingUser != null) {
				existingUser.setUsername(updatedAdmin.getUser().getUsername());
				if(updatedAdmin.getUser().getPassword() != null) {
					existingUser.setPassword(passwordEncoder.encode(updatedAdmin.getUser().getPassword()));
				}
				existingAdmin.setUser(existingUser);
			}
		}
		return adminRepo.save(existingAdmin);
	}

	@Override
	public void saveAdmin(Admin admin) {
		User user = new User();
		user.setUsername(admin.getUser().getUsername());
		user.setPassword(passwordEncoder.encode(admin.getUser().getPassword()));
		
		Optional<Role> userRole= roleRepo.findById(4);
		List<Role> roles = new ArrayList<Role>();
		if(userRole.isPresent()) {
			roles.add(userRole.get());
		}		
		user.setRoles(roles);
		
		Admin newAdmin = new Admin();
		newAdmin.setFirstname(admin.getFirstname());
		newAdmin.setLastname(admin.getLastname());
		newAdmin.setUser(user);
		
		adminRepo.save(newAdmin);
	}

}
