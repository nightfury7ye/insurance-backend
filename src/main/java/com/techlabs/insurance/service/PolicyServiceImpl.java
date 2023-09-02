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
import org.springframework.stereotype.Service;

import com.techlabs.insurance.entities.Customer;
import com.techlabs.insurance.entities.InstallmentType;
import com.techlabs.insurance.entities.InsuranceScheme;
import com.techlabs.insurance.entities.Payment;
import com.techlabs.insurance.entities.Payment_status;
import com.techlabs.insurance.entities.Policy;
import com.techlabs.insurance.entities.Status;
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
	
	@Override
	public Policy purchasePolicy(Policy policy, int customerid ,int schemeid,int investtime, int typeid, int statusid) {
		LocalDate currentDate = LocalDate.now();
		LocalDate incrementedDate = currentDate.plusYears(investtime);
		Optional<InsuranceScheme> insuranceScheme = schemeRepo.findById(schemeid);
		Optional<Customer> customer = customerRepo.findById(customerid);
		Optional<InstallmentType> installmentType = typeRepo.findById(typeid);
		Optional<Status> status = statusRepo.findById(statusid);
		if(status.isPresent()) {
			policy.setStatus(status.get());
		}else {
			policy.setStatus(statusRepo.findById(1).get());
		}
		policy.setCustomer(customer.get());
		policy.setInsuranceScheme(insuranceScheme.get());
		policy.setIssuedate(Date.valueOf(currentDate));
		policy.setMaturitydate(Date.valueOf(incrementedDate));
		policy.setInstallmentType(installmentType.get());
		return policyRepo.save(policy);
	}

	@Override
	public List<Payment> payFirstInstallment(int policyid, Payment payment) {
		LocalDate date1 = LocalDate.now();
		int count = 0;
		Policy policy = (policyRepo.findById(policyid)).get();
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
	public Page<Policy> getPoliciesByCustomer(int customerid, int pageno, int pagesize) {
		Pageable pageable = PageRequest.of(pageno, pagesize);
		return policyRepo.findByCustomerCustomerid(customerid, pageable);
	}
}
