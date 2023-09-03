package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class InsuranceSchemeNotFoundException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public InsuranceSchemeNotFoundException(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	}
	@Override
	public String toString() {
		return "InsuranceSchemeNotFoundException [message=" + message + ", status=" + status + "]";
	}
}
