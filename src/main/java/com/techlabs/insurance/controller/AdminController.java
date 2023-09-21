package com.techlabs.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entities.Admin;
import com.techlabs.insurance.service.AdminService;

@RestController
@RequestMapping("/insurance-app")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
//	@GetMapping("/users/admin")
//	public Admin getAdminById(@RequestParam(name="adminid")int adminId) {
//		return adminService.getAdminById(adminId);
//	}
	
	@PutMapping("/users/admin/{adminid}")
	public ResponseEntity<Admin> updateAdminProfile(@PathVariable(name="adminid")int adminId, @RequestBody Admin updatedAdmin) {
		return adminService.updateAdminProfile(adminId, updatedAdmin);
	}
	
	@PostMapping("/users/admin")
	public ResponseEntity<Admin> saveAdmin(@RequestBody Admin admin) {
		return adminService.saveAdmin(admin);
	}
	
	@GetMapping("/users/admin")
	public Admin getAdminByUsername(@RequestParam(name="username")String username) {
		return adminService.getAdminByUsername(username);
	}
}
