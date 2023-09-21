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
		System.out.println("Exception : " + e);
		System.out.println("Exception msg: " + e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SchemeAlreadyExistsException.class)
	public ResponseEntity<?> handleSchemeAlreadyExistsException(SchemeAlreadyExistsException e){
		System.out.println("Exception : " + e);
		System.out.println("Exception msg: " + e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidUsernameException.class)
	public ResponseEntity<?> handleInvalidUsernameException(InvalidUsernameException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RegistrationFailedException.class)
	public ResponseEntity<?> handleRegistrationFailedException(RegistrationFailedException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidFirstnameException.class)
	public ResponseEntity<?> handleInvalidFirstnameException(InvalidFirstnameException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidLastnameException.class)
	public ResponseEntity<?> handleInvalidLastnameException(InvalidLastnameException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<?> handleInvalidEmailException(InvalidEmailException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidPhonenoException.class)
	public ResponseEntity<?> handleInvalidPhonenoException(InvalidPhonenoException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidPlannameException.class)
	public ResponseEntity<?> handleInvalidPlannameException(InvalidPlannameException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidAgeException.class)
	public ResponseEntity<?> handleInvalidAgeException(InvalidAgeException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidAmountException.class)
	public ResponseEntity<?> handleInvalidAmountException(InvalidAmountException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidInvestTimeException.class)
	public ResponseEntity<?> handleInvalidInvestTimeException(InvalidInvestTimeException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidRegistrationCommissionRatioException.class)
	public ResponseEntity<?> handleInvalidRegistrationCommissionRatioException(InvalidRegistrationCommissionRatioException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidProfitRatioException.class)
	public ResponseEntity<?> handleInvalidProfitRatioException(InvalidProfitRatioException e){
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
