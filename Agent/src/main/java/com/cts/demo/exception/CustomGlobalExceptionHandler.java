package com.cts.demo.exception;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

//Marks this class as a global exception handler for all controllers
@ControllerAdvice
public class CustomGlobalExceptionHandler {

//Handles validation errors for method arguments annotated with @Valid or @Validated
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date());

//Extracts and adds all field-specific validation error messages to the response body
		ex.getBindingResult().getAllErrors().forEach(error -> {
			body.put(((FieldError) error).getField(), error.getDefaultMessage());
		});

//Returns a 400 Bad Request with validation error details
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}

//Handles custom exception when an agent is not found
	@ExceptionHandler(value = AgentNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleAdminRegistrationException(AgentNotFoundException exception,
			WebRequest webRequest) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(exception.getMessage());

//Returns a 406 Not Acceptable with custom error details
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}

//Handles all other generic exceptions
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponse> handleAccountIdException(Exception exception, WebRequest webRequest) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(exception.getMessage());

//Returns a 406 Not Acceptable for any unhandled exceptions
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}
}
