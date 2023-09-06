package com.techlabs.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.insurance.payload.CommissionDto;
import com.techlabs.insurance.service.CommisionService;

@RestController
@RequestMapping("/insurance-app")
public class CommisionController {
	
	@Autowired
	private CommisionService commisionService;
	
	@GetMapping("/agent/{agentid}/commission")
	public Page<CommissionDto> getCommisionsForAgent(@PathVariable(name="agentid") int agentid, @RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		return commisionService.getCommisionsForAgent(agentid, page, size);
	}
	
	@GetMapping("/commissions")
	public Page<CommissionDto> getCommisions(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size){
		System.out.println("Inside getCommisions");
		return commisionService.getCommisions(page, size);
	}
	
	@PutMapping("/commission/{commissionid}")
	public void withdrawCommission(@PathVariable(name="commissionid") int commissionid) {
		commisionService.withdrawCommission(commissionid);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping("/commission/{commissionid}/approve")
	public void approveCommission(int commissionid) {
		commisionService.approveCommission(commissionid);
	}
}
