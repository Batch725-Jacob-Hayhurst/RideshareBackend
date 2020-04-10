package com.revature.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception used for handling ResourceNotFound exceptions.
 * 
 * @author Timothy Mitchell
 *
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Gets the message from the resource not found exception and sets it as the message to be displayed when the exception is thrown.
	 * 
	 * @param message that is displayed when a resource not found exception is thrown
	 * @return The resource not found exception message.
	 */
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
