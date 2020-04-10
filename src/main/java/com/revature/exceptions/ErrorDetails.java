package com.revature.exceptions;

import java.util.Date;

/**
 * POJO that gets information pertaining to errors encountered during execution of the program.
 * 
 * @author Timothy Mitchell
 *
 */

public class ErrorDetails {
	private Date timestamp;
	private String message;
	private String details;

	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
}