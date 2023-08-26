package com.techlabs.insurance.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Customer {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private int customerid;
	@Column
	private String firstname;
	@Column
	private String lastname;
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="userid")
	User user;
	@Column
	private String email;
	@Column
	private long phoneno;
	
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="addressid")
	private Address address;
	
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="nomineeid")
	private Nominee nominee;
	
	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="custid")
	private List<Documents> documents;
	
	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="custid")
	private List<Policy> policy;
	
}
