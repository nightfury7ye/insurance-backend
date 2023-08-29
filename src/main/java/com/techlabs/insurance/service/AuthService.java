package com.techlabs.insurance.service;

import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.payload.LoginDto;
import com.techlabs.insurance.payload.RegisterDto;

public interface AuthService {
	String login(LoginDto loginDto);
	User register(RegisterDto registerDto, int roleid);
}
