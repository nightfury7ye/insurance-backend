package com.techlabs.insurance.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Documents;
import com.techlabs.insurance.repo.CustomerRepo;
import com.techlabs.insurance.repo.DocumentsRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentsServiceImpl implements DocumentsService{
private final String PATH = "/Users/praveenvemula/Documents/Monocept/springbootAssignments/11-insurance-app/assets/";

	@Autowired
	private DocumentsRepo documentsRepo;
	@Autowired
	private CustomerRepo customerRepo;
	
	@Override
	public Documents uploadFile(MultipartFile file, Customer customer) throws IllegalStateException, IOException{
		String fullPath = PATH+file.getOriginalFilename();
		Documents Document  = new Documents();
		Document.setName(file.getOriginalFilename());
		Document.setType(file.getContentType());
		Document.setPath(fullPath);
		Document.setCustomer(customer);
		customer.setDocumentStatus("Pending");
		customerRepo.save(customer);
		file.transferTo(new File(fullPath));
		return documentsRepo.save(Document);
	}
	
	@Override
	public byte[] downloadFile(long id) throws IOException{
		Documents Document  = documentsRepo.findById(id).get();
        String fullPath = Document.getPath();
        return Files.readAllBytes(new File(fullPath).toPath());
    }
}
