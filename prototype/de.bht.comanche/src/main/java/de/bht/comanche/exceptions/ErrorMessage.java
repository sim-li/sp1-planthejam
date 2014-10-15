package de.bht.comanche.exceptions;

public class ErrorMessage {

	/**
	 * user message
	 */
	public final String message;

	/**
	 * stack trace
	 */
	public final String stackTrace;
	
	// used with ServerException
	public ErrorMessage(String message, String stackTrace) {
		this.message = message;
		this.stackTrace = stackTrace;
	}
	
	public ErrorMessage(String errorMessage, StackTraceElement[] stackTrace) {
		this(errorMessage, stackTraceToString(stackTrace));
	}

	// TODO remove! - unused
	// used with AdapterException
	public ErrorMessage(AdapterException exception) {
//		this.status = exception.getStatus();
		this.message = exception.getErrorMessage();
		this.stackTrace = exception.gStackTrace();
	}

	// TODO is it working as it should?
	private static String stackTraceToString(StackTraceElement[] stackTrace) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement s : stackTrace) {
			sb.append(s.toString() + "\n");
		}
		return sb.toString();
	}

}
