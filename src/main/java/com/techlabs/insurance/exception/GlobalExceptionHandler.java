package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserAPIException.class)
	public ResponseEntity<?> handleUserNotFoundException(UserAPIException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ListIsEmptyException.class)
	public ResponseEntity<?> ListIsEmptyException(ListIsEmptyException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InsuranceSchemeNotFoundException.class)
    public ResponseEntity<?> handleSchemeNotFoundeException(InsuranceSchemeNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(InsurancePlanNotFoundException.class)
	public ResponseEntity<?> handleInsurancePlanNotFound(InsurancePlanNotFoundException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PolicyNotFoundException.class) 
    public ResponseEntity<?> handlePolicyNotFoundException(PolicyNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(StatusNotFoundException.class)
	public ResponseEntity<?> handleStatusNotFoundException(StatusNotFoundException e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e, WebRequest webRequest){
		System.out.println("Exception msg: " + e);
		return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(PlanAlreadyExistsException.class)
	public ResponseEntity<?> handlePlanAlreadyExistsException(PlanAlreadyExistsException e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
