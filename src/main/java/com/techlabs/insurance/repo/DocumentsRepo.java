package com.techlabs.insurance.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.insurance.entities.Documents;

public interface DocumentsRepo extends JpaRepository<Documents, Long>{

}
