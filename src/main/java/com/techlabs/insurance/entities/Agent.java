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
public class Agent {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private int agentid;
	
	@Column
	private String firstname;
	
	@Column
	private String lastname;
	
	@Column
	private String email;
	
	@Column
	private long phoneno;
	
	
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="userid")
	User user;
	
	@Column
	private String qualification;
	
	@Column
	private double totalcommision;
	
	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="agentid")
	private List<Policy> policy;
	
	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="agentid") 
	@JsonIgnore
	private List<Commision> commision;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="statusid")
	UserStatus userStatus;
	
}
