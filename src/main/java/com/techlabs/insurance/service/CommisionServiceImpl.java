package com.techlabs.insurance.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Commision;
import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Policy;
import com.techlabs.insurance.payload.CommissionDto;
import com.techlabs.insurance.repo.AgentRepo;
import com.techlabs.insurance.repo.CommisionRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommisionServiceImpl implements CommisionService{
	
	@Autowired
	private CommisionRepo commisionRepo;
	@Autowired
	private AgentRepo agentRepo;

	@Override
	public Commision saveCommision(double amount, Agent agent, Customer customer, Policy policy) {
		LocalDate date = LocalDate.now();
		Commision commision = new Commision();
		commision.setAmount(amount);
		commision.setCustomer(customer);
		commision.setAgent(agent);
		commision.setPolicy(policy);
		commision.setDate(Date.valueOf(date));
		commision.setRequestStatus("pending");
		commision.setWithdrawStatus("pending");
		return commisionRepo.save(commision);
	}

//	@Override
//	public List<Commision> getCommisionsForAgent(int agentid) {
//		Agent agent = agentRepo.findById(agentid).orElseThrow();
//		List<Commision> commision = agent.getCommision();
//		return commision;
//	}
	
	@Override
	public Page<CommissionDto> getCommisionsForAgent(int agentid, int page, int pageSize) {
		Agent agent = agentRepo.findById(agentid).orElseThrow();

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Commision> commissionPage = commisionRepo.findByAgent(agent, pageable);

        Page<CommissionDto> commissionDtoPage = commissionPage.map(this::mapToCommissionDto);

        return commissionDtoPage;
    }

    private CommissionDto mapToCommissionDto(Commision commission) {
        CommissionDto commissionDto = new CommissionDto();
        commissionDto.setCustomerUsername(commission.getCustomer().getUser().getUsername()); 
        commissionDto.setCustomerFirstname(commission.getCustomer().getFirstname()); 
        commissionDto.setAgentName(commission.getAgent().getFirstname() + " " + commission.getAgent().getLastname());
        commissionDto.setDate(commission.getDate());
        commissionDto.setPolicynumber(commission.getPolicy().getPolicyno());
        commissionDto.setRequestStatus(commission.getRequestStatus());
        commissionDto.setWithdrawStatus(commission.getWithdrawStatus());
        commissionDto.setAmount(commission.getAmount());
        commissionDto.setCommisionid(commission.getCommisionid());
        return commissionDto;
    }

	@Override
	public void withdrawCommission(int commissionid) {
		Commision commision = commisionRepo.findById(commissionid).orElseThrow();
		commision.setRequestStatus("requested");
		commisionRepo.save(commision);
	}

	@Override
	public Page<CommissionDto> getCommisions(int page, int pageSize) {
		 Pageable pageable = PageRequest.of(page, pageSize);
		 Page<Commision> commissionPage = commisionRepo.findAll(pageable);
	     Page<CommissionDto> commissionDtoPage = commissionPage.map(this::mapToCommissionDto);
	     return commissionDtoPage;
	}
	

	@Override
	public void approveCommission(int commissionid) {
		System.out.print(commissionid);
		Commision commision = commisionRepo.findById(commissionid).orElseThrow();
		commision.setWithdrawStatus("approved");
		commisionRepo.save(commision);
	}
	
}
	

