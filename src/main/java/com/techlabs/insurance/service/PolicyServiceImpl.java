package com.techlabs.insurance.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entities.Agent;
import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.Employee;
import com.techlabs.insurance.entities.InstallmentType;
import com.techlabs.insurance.entities.InsuranceScheme;
import com.techlabs.insurance.entities.Payment;
import com.techlabs.insurance.entities.Payment_status;
import com.techlabs.insurance.entities.Policy;
import com.techlabs.insurance.entities.Status;
import com.techlabs.insurance.exception.InsurancePlanNotFoundException;
import com.techlabs.insurance.exception.InsuranceSchemeNotFoundException;
import com.techlabs.insurance.exception.ListIsEmptyException;
import com.techlabs.insurance.exception.PolicyNotFoundException;
import com.techlabs.insurance.exception.UserAPIException;
import com.techlabs.insurance.repo.AgentRepo;
import com.techlabs.insurance.repo.CustomerRepo;
import com.techlabs.insurance.repo.InstallmentTypeRepo;
import com.techlabs.insurance.repo.InsuranceSchemeRepo;
import com.techlabs.insurance.repo.PaymentRepo;
import com.techlabs.insurance.repo.PaymentStatusRepo;
import com.techlabs.insurance.repo.PolicyRepo;
import com.techlabs.insurance.repo.StatusRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService{
	
	@Autowired
	private InsuranceSchemeRepo schemeRepo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private PolicyRepo policyRepo;
	@Autowired
	private InstallmentTypeRepo typeRepo;
	@Autowired
	private StatusRepo statusRepo;
	@Autowired
	private PaymentStatusRepo paymentStatusRepo;
	@Autowired
	private PaymentRepo paymentRepo;
	@Autowired
	private AgentRepo agentRepo;
	@Autowired
	private CommisionService commisionService;
	
	@Override
	public Policy purchasePolicy(Policy policy, int customerid ,int schemeid,int investtime, int typeid, int statusid) {
		LocalDate currentDate = LocalDate.now();
		LocalDate incrementedDate = currentDate.plusYears(investtime);
		InsuranceScheme insuranceScheme = schemeRepo.findById(schemeid).orElseThrow(()-> new InsuranceSchemeNotFoundException(HttpStatus.BAD_REQUEST,"Insurance Scheme Not Found!!!"));
		Customer customer = customerRepo.findById(customerid).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Customer Not Found!!!"));;
		Optional<InstallmentType> installmentType = typeRepo.findById(typeid);
		Optional<Status> status = statusRepo.findById(statusid);
		if(status.isPresent()) {
			policy.setStatus(status.get());
		}else {
			policy.setStatus(statusRepo.findById(1).get());
		}
		policy.setCustomer(customer);
		policy.setInsuranceScheme(insuranceScheme);
		policy.setIssuedate(Date.valueOf(currentDate));
		policy.setMaturitydate(Date.valueOf(incrementedDate));
		policy.setInstallmentType(installmentType.get());
		return policyRepo.save(policy);
	}
	
	@Override
	public Policy purchasePolicyViaAgent(Policy policy, int customerid, int agentid, int schemeid, int investtime,
			int typeid, int statusid) {
		LocalDate currentDate = LocalDate.now();
		LocalDate incrementedDate = currentDate.plusYears(investtime);
		InsuranceScheme insuranceScheme = schemeRepo.findById(schemeid).orElseThrow(()-> new InsuranceSchemeNotFoundException(HttpStatus.BAD_REQUEST,"Insurance Scheme Not Found!!!"));
		Customer customer = customerRepo.findById(customerid).orElseThrow(()-> new UserAPIException(HttpStatus.BAD_REQUEST,"Customer Not Found!!!"));;
		Optional<InstallmentType> installmentType = typeRepo.findById(typeid);
		Optional<Status> status = statusRepo.findById(statusid);
		if(status.isPresent()) {
			policy.setStatus(status.get());
		}else {
			policy.setStatus(statusRepo.findById(1).get());
		}
		Optional<Agent> agent = agentRepo.findById(agentid);
		if(agent.isPresent()) {
			policy.setAgent(agent.get());
		}
		policy.setCustomer(customer);
		policy.setInsuranceScheme(insuranceScheme);
		policy.setIssuedate(Date.valueOf(currentDate));
		policy.setMaturitydate(Date.valueOf(incrementedDate));
		policy.setInstallmentType(installmentType.get());
		int commratio = insuranceScheme.getSchemeDetails().getRegistrationcommratio();
		double commisionAmount = (commratio/ 100.0) * policy.getSumassured();
		commisionService.saveCommision(commisionAmount, agent.get(), customer, policy);
		return policyRepo.save(policy);
	}

	@Override
	public List<Payment> payFirstinstallment(int policyid, Payment payment) {
		LocalDate date1 = LocalDate.now();
		int count = 0;
		Policy policy = policyRepo.findById(policyid).orElseThrow(()-> new PolicyNotFoundException(HttpStatus.BAD_REQUEST,"Policy Plan Not Found!!!"));
		LocalDate issueDate = policy.getIssuedate().toLocalDate();
		LocalDate maturityDate = policy.getMaturitydate().toLocalDate();
		Period period = issueDate.until(maturityDate);
		int years = period.getYears();
		List<Payment> payments = policy.getPaidpremiums();
		if(policy.getInstallmentType().getType_value()==12) {
			count = years;
			for(int i = 0; i < count; i++) {
				if(i == 0) {
					System.out.println("inside 0");
					payment.setDate(Date.valueOf(date1));
					Optional<Payment_status> status= paymentStatusRepo.findById(1);
					payment.setStatus(status.get());
					payments.add(payment);
				}
				else {
					Payment emptyPayment = new Payment();
					emptyPayment.setAmount(policy.getPremiumamount());
					emptyPayment.setTotalpayment(payment.getTotalpayment());
					emptyPayment.setTax(payment.getTax());
					date1 = Date.valueOf(date1.plusYears(1)).toLocalDate();
					emptyPayment.setDate(Date.valueOf(date1));
					Optional<Payment_status> status = paymentStatusRepo.findById(2);
					emptyPayment.setStatus(status.get());
					payments.add(emptyPayment);
				}
			}
		}
		else if(policy.getInstallmentType().getType_value()==6) {
			count = years * 2;
			for(int i = 0; i < count; i++) {
				if(i == 0) {
					payment.setDate(Date.valueOf(date1));
					Optional<Payment_status> status= paymentStatusRepo.findById(1);
					payment.setStatus(status.get());
					payment.setAmount(policy.getPremiumamount());
				}
				else {
					Payment emptyPayment = new Payment();
					emptyPayment.setAmount(policy.getPremiumamount());
					emptyPayment.setTotalpayment(payment.getTotalpayment());
					emptyPayment.setTax(payment.getTax());
					date1 = Date.valueOf(date1.plusMonths(6)).toLocalDate();
					emptyPayment.setDate(Date.valueOf(date1));
					Optional<Payment_status> status = paymentStatusRepo.findById(2);
					emptyPayment.setStatus(status.get());
					payments.add(emptyPayment);
				}
				payments.add(payment);
			}
		}
		else if(policy.getInstallmentType().getType_value()==3) {
			count = years * 4;
			for(int i = 0; i < count; i++) {
				if(i == 0) {
					payment.setDate(Date.valueOf(date1));
					Optional<Payment_status> status= paymentStatusRepo.findById(1);
					payment.setStatus(status.get());
				}
				else {
					Payment emptyPayment = new Payment();
					emptyPayment.setAmount(policy.getPremiumamount());
					emptyPayment.setTotalpayment(payment.getTotalpayment());
					emptyPayment.setTax(payment.getTax());
					date1 = Date.valueOf(date1.plusMonths(3)).toLocalDate();
					emptyPayment.setDate(Date.valueOf(date1));
					Optional<Payment_status> status = paymentStatusRepo.findById(2);
					emptyPayment.setStatus(status.get());
					payments.add(emptyPayment);
				}
				payments.add(payment);
			}
		}
		policy.setPaidpremiums(payments);
		policyRepo.save(policy);
		return payments;
	}

	@Override
	public Payment payInstallment(Payment payment ,int paymentid) {
		System.out.println("pay");
		Payment dbpayment = paymentRepo.findById(paymentid).get();
		dbpayment.setPaymenttype(payment.getPaymenttype());
		Optional<Payment_status> status= paymentStatusRepo.findById(1);
		dbpayment.setStatus(status.get());
		return paymentRepo.save(dbpayment);
	}

	@Override
	public ResponseEntity<Page<Policy>> getPoliciesByCustomer(int customerid, int pageno, int pagesize) {
		Pageable pageable = PageRequest.of(pageno, pagesize);
		Page<Policy> policies =policyRepo.findAll(pageable);
		if(policies.isEmpty()) {
			throw new ListIsEmptyException(HttpStatus.BAD_REQUEST, "Policies List Is Empty!!!");
		}
		return new ResponseEntity<>(policies,HttpStatus.OK) ;
	}

	@Override
	public Policy getPolicy(int policyid) {
		Policy policy = policyRepo.findById(policyid).orElseThrow(()-> new InsuranceSchemeNotFoundException(HttpStatus.BAD_REQUEST,"Insurance Scheme Not Found!!!"));;
		return policy;
		
	}
}
