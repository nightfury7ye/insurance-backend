package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class PolicyNotFoundException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public PolicyNotFoundException(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	@Override
	public String toString() {
		return "PolicyNotFoundException [message=" + message + ", status=" + status + "]";
	}
}
