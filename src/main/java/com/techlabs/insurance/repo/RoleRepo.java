package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
