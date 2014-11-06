package de.bht.comanche.rest;

import java.util.Locale;
import java.util.ResourceBundle;
import multex.Msg;

public class ReErrorMessage {

	/**
	 * user message
	 */
	public final String message;

	/**
	 * stack trace
	 */
	public final String stackTrace;
	
	public static final String BASE_NAME = "MessageResources";
	
	
	public ReErrorMessage(String message, String stackTrace) {
		this.message = message;
		this.stackTrace = stackTrace;
	}
	
	/**
	 * 
	 * @param ex The exception, which has to be reported.
	 * @param stackTrace The stacktrace, which has to be reported.
	 */
	public ReErrorMessage(Throwable ex, String stackTrace) {
		this(exceptionToString(ex), stackTrace);
	}
	
	public ReErrorMessage(String errorMessage, StackTraceElement[] stackTrace) {
		this(errorMessage, stackTraceToString(stackTrace));
	}
	
	private static String exceptionToString(Throwable ex){
		final StringBuffer result = new StringBuffer();
		final Locale defaultLocale = Locale.getDefault();
		final ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, defaultLocale);
        Msg.printMessages(result, ex, bundle);
		return result.toString();
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
