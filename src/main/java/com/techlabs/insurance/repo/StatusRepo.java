package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Status;

public interface StatusRepo extends JpaRepository<Status, Integer>{

}
