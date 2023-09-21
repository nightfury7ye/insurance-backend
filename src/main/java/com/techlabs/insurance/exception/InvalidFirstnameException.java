package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class InvalidFirstnameException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public InvalidFirstnameException(HttpStatus status, String message) {
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
		return "InvalidFirstnameException [message=" + message + ", status=" + status + "]";
	}
}
