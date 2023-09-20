package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class InsufficientBalanceException {
	private String message;
	private HttpStatus status;
	public InsufficientBalanceException(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	@Override
	public String toString() {
		return "ListIsEmptyException [message=" + message + ", status=" + status + "]";
	}
}
