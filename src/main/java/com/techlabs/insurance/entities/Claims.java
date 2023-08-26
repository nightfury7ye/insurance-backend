package com.techlabs.insurance.entities;

import java.sql.Date;

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
public class Claims {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private int claimid;
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="policyno")
	private Policy policy;
	@Column
	private double claim_amount;
	@Column
	private long bank_accno;
	@Column
	private String bank_ifsc_code;
	@Column
	private Date date;
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="statusid")
	Status status;
}
