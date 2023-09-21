package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class InvalidPlannameException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public InvalidPlannameException(HttpStatus status, String message) {
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
		return "InvalidPlannameException [message=" + message + ", status=" + status + "]";
	}
}
