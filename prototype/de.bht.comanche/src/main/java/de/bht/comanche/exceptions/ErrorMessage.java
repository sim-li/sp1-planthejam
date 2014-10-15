package de.bht.comanche.exceptions;

public class ErrorMessage {

	/**
	 * HTTP status of the response
	 */
	int status;

	/**
	 * user message
	 */
	String errorMessage;

	/**
	 * stack Trace
	 */
	private String stackTrace;

	// used with TestException
	public ErrorMessage(int status, String errorMessage, String stackTrace) {
		this.status = status;
		this.errorMessage = errorMessage;
		this.stackTrace = stackTrace;
	}

	// used with AdapterException
	public ErrorMessage(AdapterException exception) {
		this.status = exception.getStatus();
		this.errorMessage = exception.getErrorMessage();
		this.stackTrace = exception.gStackTrace();
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
