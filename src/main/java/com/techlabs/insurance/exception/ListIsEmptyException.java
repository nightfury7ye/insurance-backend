package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class ListIsEmptyException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public ListIsEmptyException(HttpStatus status, String message) {
		super();
		this.message = message;
		this.status = status;
	}
	@Override
	public String toString() {
		return "ListIsEmptyException [message=" + message + ", status=" + status + "]";
	}
}
