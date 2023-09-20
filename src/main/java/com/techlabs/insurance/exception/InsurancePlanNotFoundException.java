package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class InsurancePlanNotFoundException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public InsurancePlanNotFoundException(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	@Override
	public String toString() {
		return "InsurancePlanNotFoundException [message=" + message + ", status=" + status + "]";
	}
}
