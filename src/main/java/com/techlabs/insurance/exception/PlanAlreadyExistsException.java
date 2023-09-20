package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class PlanAlreadyExistsException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public PlanAlreadyExistsException(HttpStatus status, String message) {
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
		return "PlanAlreadyExistsException [message=" + message + ", status=" + status + "]";
	}
}
