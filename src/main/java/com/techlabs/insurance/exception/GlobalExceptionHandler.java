package com.techlabs.insurance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
	
//	@ExceptionHandler(AccountNotFoundException.class)
//	public ResponseEntity<?> handleAccountNotFound(AccountNotFoundException e){
//		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//	}
//	
//	@ExceptionHandler(AccountStatusException.class)
//	public ResponseEntity<?> handleAccountStatusException(AccountStatusException e){
//		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//	}
//	
	
//	@ExceptionHandler(IllegalArgumentExceptionCustom.class)
//	public ResponseEntity<?> handleInsurancePlanNotFound(IllegalArgumentExceptionCustom e) {
//		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//	}
//	
	
//	
//	@ExceptionHandler(AgentNotFoundException.class)
//    public ResponseEntity<?> handleAgentNotFoundException(AgentNotFoundException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(InsufficientBalanceException.class)
//    public ResponseEntity<?> handleInsufficientBalanceException(InsufficientBalanceException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//    
//    @ExceptionHandler(ClaimNotFoundException.class)
//    public ResponseEntity<?> handleClaimNotFoundeException(ClaimNotFoundException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//    
    
}
