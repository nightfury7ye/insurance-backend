package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class StatusNotFoundException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public StatusNotFoundException(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	}
	@Override
	public String toString() {
		return "StatusNotFoundException [message=" + message + ", status=" + status + "]";
	}
}
