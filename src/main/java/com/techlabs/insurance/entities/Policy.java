package com.techlabs.insurance.entities;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Policy {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private int policyno;
	@Column
	private Date issuedate;
	@Column
	private Date maturitydate;
	@Column
	private double premiumamount;
	
	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="policyno")
	@JsonIgnore
	private List<Payment> paidpremiums;
	
	@Column
	private double sumassured;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="typeid")
	private InstallmentType installmentType;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="custid")
	@JsonIgnore
	private Customer customer;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="schemeid")
	private InsuranceScheme insuranceScheme;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="agentid")
	private Agent agent;
	
	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="policyno")
	private List<Claims> claims;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="statusid")
	Status status;
	
}
