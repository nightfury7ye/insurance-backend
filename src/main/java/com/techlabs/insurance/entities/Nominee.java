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
public class Nominee {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column
	private int nomineeid;
	@Column
	private String nomineename;
	@Column
	private String nomineeRelation;
}
