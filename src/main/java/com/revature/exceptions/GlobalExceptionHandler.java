package com.revature.exceptions;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Class that gets information pertaining to exceptions encountered during execution of the program and handles those exceptions.
 * 
 * @author Timothy Mitchell
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * Gets the web request and resource not found exception and returns a response entity detailing the exception.
	 * 
	 * @param ex from the custom resource not found exception handler
	 * @param request from the http request
	 * @return The specifics of the resource not found exception.
	 */
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * Gets the web request and a generic exception and returns a response entity detailing the exception.
	 * 
	 * @param ex is the exception thrown during execution of the program
	 * @param request from the http request
	 * @return The specifics of the exception.
	 */
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}