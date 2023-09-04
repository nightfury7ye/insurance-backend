package com.techlabs.insurance.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Commision {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private int commisionid;
	@Column
	private double amount;
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="agentid")
	private Agent agent;
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="customerid")
	private Customer customer;
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="policyno")
	private Policy policy;
}
