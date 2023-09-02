package com.techlabs.insurance.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Documents;

public interface DocumentsService {

	Documents uploadFile(MultipartFile file, Customer customer) throws IllegalStateException, IOException;
	
	byte[] downloadFile(long id) throws IOException;

}
