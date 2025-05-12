package com.cts.demo.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

	private int status;
	private String message;
	private LocalDateTime time;

	public ExceptionResponse() {
		super();
	}
}
