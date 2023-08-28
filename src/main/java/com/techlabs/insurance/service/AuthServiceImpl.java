package com.techlabs.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entities.Role;
import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.payload.LoginDto;
import com.techlabs.insurance.payload.RegisterDto;
import com.techlabs.insurance.repo.RoleRepo;
import com.techlabs.insurance.repo.UserRepo;
import com.techlabs.insurance.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService{
	private AuthenticationManager authenticationManager;
	private UserRepo userRepo;
	private RoleRepo roleRepo;
	private JwtTokenProvider jwtTokenProvider; 
	private PasswordEncoder passwordEncoder;
	
	public AuthServiceImpl(AuthenticationManager authenticationManager,
			UserRepo userRepo, RoleRepo roleRepo,
			JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.jwtTokenProvider = jwtTokenProvider;
		this.passwordEncoder = passwordEncoder;
	}
	@Override
	public String login(LoginDto loginDto) {
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication); 
		String token=jwtTokenProvider.generateToken(authentication);
		return token;
	}
	@Override
	public String register(RegisterDto registerDto, int roleid) {
		if(userRepo.existsByUsername(registerDto.getUsername())) 
			throw new UserAPIException(HttpStatus.BAD_REQUEST, "User already exists");

		User user=new User();
		user.setUsername(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//		user.setUser(registerDto.getUser());
		
		Optional<Role> userRole= roleRepo.findById(roleid);
		List<Role> roles = new ArrayList<Role>();
		if(userRole.isPresent()) {
			roles.add(userRole.get());
		}		
		user.setRoles(roles);
		userRepo.save(user);
		System.out.println(user);

		return "User registered successfully";
	}
	
}
