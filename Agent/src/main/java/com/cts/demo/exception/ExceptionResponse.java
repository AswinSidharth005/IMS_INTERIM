package com.cts.demo.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

//Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@Data

//Lombok annotation to generate a constructor with all fields as parameters
@AllArgsConstructor
public class ExceptionResponse {

//HTTP status code (e.g., 404, 500)
	private int status;

//Error message describing the exception
	private String message;

//Timestamp when the exception occurred
	private LocalDateTime time;

//No-argument constructor (needed for serialization/deserialization)
	public ExceptionResponse() {
		super();
	}
}
