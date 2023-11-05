package com.mobileskins.productsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FileNotFoundException extends RuntimeException{
	
	 public FileNotFoundException(String message) {
	     super(message);
	 }

	 public FileNotFoundException(String message, Throwable cause) {
	     super(message, cause);
	 }

}
