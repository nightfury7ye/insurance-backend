package com.techlabs.insurance.payload;


import java.util.List;

import com.techlabs.insurance.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class JwtAuthResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private List<Role> roles;
//	private User user;
}
