package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class ClaimNotFoundException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public ClaimNotFoundException(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	}
	@Override
	public String toString() {
		return "ClaimNotFoundException [message=" + message + ", status=" + status + "]";
	}
}
