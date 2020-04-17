package com.revature.exceptions;

import java.util.Date;

/**
 * POJO that specifies the specific error details to print in the case of any errors thrown.
 * 
 * @author Judson Higley
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