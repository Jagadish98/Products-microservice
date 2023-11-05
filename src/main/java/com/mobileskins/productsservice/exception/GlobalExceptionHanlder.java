package com.mobileskins.productsservice.exception;


import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;




@RestControllerAdvice
public class GlobalExceptionHanlder extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
																		WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(
										LocalDateTime.now(),
										exception.getMessage(),
										request.getDescription(false),
										"PRODUCT_NOT_FOUND");
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler(FileStorageException.class)
	public ResponseEntity<ErrorDetails> handleStorageException(FileStorageException ex, 
																   WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(
										LocalDateTime.now(),
										ex.getMessage(),
										request.getDescription(false),
										"COULDN'T_UPLOAD_IMAGE");
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}
	
	
	
	@ExceptionHandler(SkuAlreadyExistsException.class)
	public ResponseEntity<ErrorDetails> handleSkuAlreadyExistsException(SkuAlreadyExistsException exception,
																		WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(
										LocalDateTime.now(),
										exception.getMessage(),
										request.getDescription(false),
										"SKU_ALREADY_EXISTS");
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
	}
	

	@Override
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
																	  HttpHeaders headers, 
																	  HttpStatusCode status, 
																	  WebRequest request) {

		ErrorDetails errorDetails = new ErrorDetails(
										LocalDateTime.now(),
										ex.getMessage() + " for " + request.getDescription(false),
										request.getDescription(false),
										"URL_NOT_FOUND");
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
															   HttpHeaders headers, 
															   HttpStatusCode status, 
															   WebRequest request){
		Map<String, String> errors = new HashMap<>();
		List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
		errorList.forEach ( error -> {
			String FieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(FieldName, message);
		});
		
		ValidationErrors validationErrors = new ValidationErrors(
												LocalDateTime.now(),
												"Validation failed for one or more Product properties.",
												request.getDescription(false),
												"VALIDATION_FAILED",
												errors);
		
		return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
		
	} 
	
	
	
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintsViolationException(ConstraintViolationException ex,
																 WebRequest request) {
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

		Map<String, String> errors = new HashMap<>();
		
		violations.forEach(violation -> {
			
			String message = violation.getMessage();
			Path propertyPath = violation.getPropertyPath();
			
			String propertyPathString = propertyPath.toString();
			String partialPathString = propertyPathString.substring(propertyPathString.indexOf(".")+1);
	
			String FieldName = partialPathString.replace("productDtoList", "product");
			
			errors.put(FieldName, message);
				
		});
		
	    ValidationErrors validationErrors = new ValidationErrors(
				LocalDateTime.now(),
				"Validation failed for one or more Product properties.",
				request.getDescription(false),
				"VALIDATION_FAILED",
				errors
				);
	    
		return new ResponseEntity<>(validationErrors, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException ex,
														 WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(
				LocalDateTime.now(),
				ex.getMessage(),
				request.getDescription(false),
				"INTERNAL_SERVER_ERROR");
		
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(
										LocalDateTime.now(),
										ex.getMessage(),
										request.getDescription(false),
										"INTERNAL_SERVER_ERROR");
		
		
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
