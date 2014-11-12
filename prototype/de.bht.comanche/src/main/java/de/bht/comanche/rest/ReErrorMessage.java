package de.bht.comanche.rest;

public class ReErrorMessage {

	/**
	 * user message
	 */
	public final String message;

	/**
	 * stack trace
	 */
	public final String stackTrace;
	
	// used with ServerException
	public ReErrorMessage(String message, String stackTrace) {
		this.message = message;
		this.stackTrace = stackTrace;
	}
	
	public ReErrorMessage(String errorMessage, StackTraceElement[] stackTrace) {
		this(errorMessage, stackTraceToString(stackTrace));
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
