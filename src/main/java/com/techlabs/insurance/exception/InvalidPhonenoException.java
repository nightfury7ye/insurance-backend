package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class InvalidPhonenoException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public InvalidPhonenoException(HttpStatus status, String message) {
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
		return "InvalidPhonenoException [message=" + message + ", status=" + status + "]";
	}
}
