package com.mobileskins.productsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Setter
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SkuAlreadyExistsException extends RuntimeException{
	
	private String resourceName;
	private String fieldName;
	private String fieldValue;
	
	public SkuAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s already exists with %s : %s", resourceName, fieldName, fieldValue));
		
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		
	}

}
