package com.techlabs.insurance.service;

import com.techlabs.insurance.entities.Admin;

public interface AdminService {
	public Admin getAdminById(int adminId);
	public Admin updateAdminProfile(int adminId, Admin updatedAdmin);
	public void saveAdmin(Admin admin);
	public Admin getAdminByUsername(String username);
}
