package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class SchemeAlreadyExistsException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public SchemeAlreadyExistsException(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	@Override
	public String toString() {
		return "SchemeAlreadyExistsException [message=" + message + ", status=" + status + "]";
	}
}
