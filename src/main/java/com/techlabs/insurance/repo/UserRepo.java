package com.techlabs.insurance.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	public Optional<User> findByUsername(String username);

	public boolean existsByUsername(String username);
}
