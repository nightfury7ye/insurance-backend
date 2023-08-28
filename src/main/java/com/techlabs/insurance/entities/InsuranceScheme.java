package com.techlabs.insurance.entities;

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
import jakarta.persistence.OneToOne;
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
public class InsuranceScheme {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private int schemeid;
	@Column
	private String scheme_name;
	
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="detailid")
	private SchemeDetails schemeDetails;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="planid")
	@JsonIgnore
	private InsurancePlan plan;
	
	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="schemeid")
	private List<Policy> policy;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="statusid")
	Status status;
}
