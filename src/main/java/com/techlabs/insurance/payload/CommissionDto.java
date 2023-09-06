package com.techlabs.insurance.payload;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CommissionDto {
	private int commisionid;
	private String customerUsername;
	private String customerFirstname;
	private String agentName;
	private Date date;
	private int policynumber;
	private double amount;
	private String RequestStatus;
	private String WithdrawStatus;
}
