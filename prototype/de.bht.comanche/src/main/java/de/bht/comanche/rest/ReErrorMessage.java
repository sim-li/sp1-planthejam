package de.bht.comanche.rest;

import java.util.Locale;
import java.util.ResourceBundle;
import multex.Msg;

public class ReErrorMessage {

	public static final String BASE_NAME = "MessageResources";
	
	/**
	 * user message
	 */
	public final String message;

	/**
	 * stack trace
	 */
	public final String stackTrace;
	
	public ReErrorMessage(final String message, final String stackTrace) {
		this.message = message;
		this.stackTrace = stackTrace;
	}
	
	/**
	 * ...
	 * 
	 * @param ex The exception, which has to be reported.
	 * @param stackTrace The stack trace, which has to be reported.
	 */
	public ReErrorMessage(final Throwable ex, final String stackTrace) {
		this(exceptionToString(ex), stackTrace);
	}
	
	// TODO not used -> remove
//	public ReErrorMessage(final String errorMessage, final StackTraceElement[] stackTrace) {
//		this(errorMessage, stackTraceToString(stackTrace));
//	}
	
	private static String exceptionToString(final Throwable ex){
		final ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, Locale.getDefault());
		final StringBuffer result = new StringBuffer();
        Msg.printMessages(result, ex, bundle);
		return result.toString();
	}

	// TODO not used -> remove
//	private static String stackTraceToString(final StackTraceElement[] stackTrace) {
//		final StringBuilder sb = new StringBuilder();
//		for (final StackTraceElement s : stackTrace) {
//			sb.append(s.toString() + "\n");
//		}
//		return sb.toString();
//	}
}
