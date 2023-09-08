package com.techlabs.insurance.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Admin {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private int adminid;
	
	@Column
	private String firstname;
	
	@Column
	private String email;
	
	@Column
	private long phoneno;
	
	@Column
	private String lastname;
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="userid")
	User user;
}
