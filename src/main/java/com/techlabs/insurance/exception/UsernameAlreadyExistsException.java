package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public UsernameAlreadyExistsException(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	}
	@Override
	public String toString() {
		return "UsernameAlreadyExistsException [message=" + message + ", status=" + status + "]";
	}
}
