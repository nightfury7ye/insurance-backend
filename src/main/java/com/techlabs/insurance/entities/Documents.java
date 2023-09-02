package com.techlabs.insurance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Documents {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private Long documentid;
	@Column
	private String name;
	@Column
	private String type; 
	@Column
	private String path;
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="custid")
	@JsonIgnore
	private Customer customer;
}
