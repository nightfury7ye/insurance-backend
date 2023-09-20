package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public UsernameAlreadyExistsException(String message,HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	@Override
	public String toString() {
		return "UsernameAlreadyExistsException [message=" + message + ", status=" + status + "]";
	}
}
