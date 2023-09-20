package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class InvalidEmailException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public InvalidEmailException(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "InvalidEmailException [message=" + message + ", status=" + status + "]";
	}
}

