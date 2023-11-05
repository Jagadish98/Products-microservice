package com.mobileskins.productsservice.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidationErrors extends ErrorDetails{
	
	private Map<String, String> errors;
	
	public ValidationErrors(LocalDateTime timestamp, String message, String path, String ErrorCode, Map<String, String> errors) {
		
		this.timestamp = timestamp;
		this.message = message;
		this.path = path;
		this.ErrorCode = ErrorCode;
		this.errors = errors;
		
	}

}
