package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.User_status;

public interface UserStatusRepo extends JpaRepository<User_status, Integer>{

}
