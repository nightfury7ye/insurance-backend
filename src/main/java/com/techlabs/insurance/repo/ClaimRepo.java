package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Claim;

public interface ClaimRepo extends JpaRepository<Claim, Integer>{

}
