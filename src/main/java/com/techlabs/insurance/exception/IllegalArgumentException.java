package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IllegalArgumentException extends RuntimeException{
	private String message;
	private HttpStatus status;
	public IllegalArgumentException(String message, HttpStatus status) {
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
		return "IllegalArgumentException [message=" + message + ", status=" + status + "]";
	}
}

