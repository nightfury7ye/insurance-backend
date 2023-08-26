package com.techlabs.insurance.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.entities.User;
import com.techlabs.insurance.payload.JwtAuthResponse;
import com.techlabs.insurance.payload.LoginDto;
import com.techlabs.insurance.payload.RegisterDto;
import com.techlabs.insurance.repo.UserRepo;
import com.techlabs.insurance.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class AuthController {
	
	@Autowired
	private AuthService authservice;
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping(value = { "/login", "/signin"})
	public ResponseEntity<JwtAuthResponse> login (@RequestBody LoginDto loginDto){
		String token = authservice.login(loginDto);
		System.out.println("inside login");
		Optional<User> user = userRepo.findByUsername(loginDto.getUsername());
		JwtAuthResponse jwtAuthResponse =new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		if(user.isPresent()) {
//			jwtAuthResponse.setUser(user.get());
			jwtAuthResponse.setRoles(user.get().getRoles());
		}
		return ResponseEntity.ok(jwtAuthResponse);
	}

	@PostMapping("/register/{roleid}")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto,@PathVariable(name="roleid") int roleid){
	System.out.println("This is Register method");
	String response = authservice.register(registerDto, roleid);
	return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
