package com.eviden.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerError{
	
	@ExceptionHandler(ExceptionPageNotFound.class)
	public ResponseEntity<ApiError> handleExceptionPageNotFound(ExceptionPageNotFound e){
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
		
	}
	
	@ExceptionHandler(ExceptionValueNotRight.class)
	public ResponseEntity<ApiError> handleExceptionValueNotRight(ExceptionValueNotRight e){
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
		
	}
	
	@ExceptionHandler(ExceptionCredentialNotValid.class)
	public ResponseEntity<ApiError> handleExceptionCredentialNotValid(ExceptionCredentialNotValid e){
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
		
	}

	@ExceptionHandler(ExceptionTokenNotValid.class)
	public ResponseEntity<ApiError> handleExceptionTokenNotValid(ExceptionTokenNotValid e){
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
		
	}

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleExceptionGeneral(Exception e){
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
		
	}
	
	
}
