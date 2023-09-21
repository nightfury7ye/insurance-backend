package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

public class InvalidProfitRatioException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public InvalidProfitRatioException(HttpStatus status, String message) {
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
		return "InvalidProfitRatioException [message=" + message + ", status=" + status + "]";
	}
}
