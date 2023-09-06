package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer>{

}
