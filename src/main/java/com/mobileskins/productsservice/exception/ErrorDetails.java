package com.mobileskins.productsservice.exception;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class ErrorDetails {
	
	protected LocalDateTime timestamp;
	protected String message;
	protected String path;
	protected String ErrorCode;

}
