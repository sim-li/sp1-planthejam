package de.bht.comanche.exceptions;

public class ErrorMessage {

	/**
	 * user message
	 */
	public final String errorMessage;

	/**
	 * stack Trace
	 */
	public final String stackTrace;

	// used with TestException
	public ErrorMessage(String errorMessage, String stackTrace) {
		this.errorMessage = errorMessage;
		this.stackTrace = stackTrace;
	}

	// used with AdapterException
	public ErrorMessage(AdapterException exception) {
//		this.status = exception.getStatus();
		this.errorMessage = exception.getErrorMessage();
		this.stackTrace = exception.gStackTrace();
	}



}
