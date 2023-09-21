package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.SchemeDetails;

public interface SchemeDetailsRepo extends JpaRepository<SchemeDetails, Integer>{

}
