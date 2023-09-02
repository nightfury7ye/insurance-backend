package com.techlabs.insurance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Documents;
import com.techlabs.insurance.entities.InsurancePlan;
import com.techlabs.insurance.entities.InsuranceScheme;
import com.techlabs.insurance.repo.CustomerRepo;
import com.techlabs.insurance.repo.DocumentsRepo;
import com.techlabs.insurance.service.DocumentsService;
import com.techlabs.insurance.service.InsurancePlanService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/servicesapp")
public class CommonController {
	@Autowired
	private InsurancePlanService insurancePlanService;
	@Autowired
	private DocumentsService documentsService;
	@Autowired
	private DocumentsRepo documentsRepo;
	@Autowired
	private CustomerRepo customerRepo;
	
	@GetMapping("/getplans")
	public List<InsurancePlan> getInsurancePlans() {
		return insurancePlanService.getInsurancePlans();
	}
	
	@GetMapping("/getscheme/{planid}")
	List<InsuranceScheme> getInsuranceSchemeById(@PathVariable(name="planid") int planid){
		return insurancePlanService.getInsuranceSchemeById(planid);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("custid") int customerid) throws IllegalStateException,IOException{
		try {
			Optional<Customer> customer = customerRepo.findById(customerid);
			documentsService.uploadFile(file, customer.get());
			return "Uploaded successfuly";
		} catch (IllegalStateException | java.io.IOException e) {
			return "Error uploading";
		}
	}
	
	@GetMapping("/download/{fileid}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable(name="fileid") long id) throws IOException, java.io.IOException {
		byte[] file = documentsService.downloadFile(id);
		Optional<Documents> document = documentsRepo.findById(id);
		String mediaType = document.get().getType();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(mediaType)).body(file);
	}
}
