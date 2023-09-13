package com.techlabs.insurance.service;

import org.springframework.http.ResponseEntity;

import com.techlabs.insurance.entities.Admin;

public interface AdminService {
	public Admin getAdminById(int adminId);
	public Admin updateAdminProfile(int adminId, Admin updatedAdmin);
	public ResponseEntity<Admin> saveAdmin(Admin admin);
	public Admin getAdminByUsername(String username);
}
