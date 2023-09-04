package com.techlabs.insurance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class SchemeDetails {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private int detailid;
	@Column
	private String discription;
	@Column
	private double min_amount;
	@Column
	private double max_amount;
	@Column
	private int min_invest_time;
	@Column
	private int max_invest_time;
	@Column
	private int min_age;
	@Column
	private int max_age;
	@Column
	private int profit_ratio;
	@Column
	private int registrationcommratio;
	
	
}
