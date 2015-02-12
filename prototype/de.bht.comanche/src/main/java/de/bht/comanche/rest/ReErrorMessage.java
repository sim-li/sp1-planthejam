package de.bht.comanche.rest;

import java.util.Locale;
import java.util.ResourceBundle;
import multex.Msg;

/**
 * This class represents an error message. Provide general user information about the error
 * and detail stack trace for debugging.
 * @author Maxim Novichkov
 */
public class ReErrorMessage {
	
	/**
	 * The baseName for locating the exception message text resource bundle.
	 */
	public static final String BASE_NAME = "MessageResources";
	
	/**
	 * user message
	 */
	public final String message;

	/**
	 * stack trace
	 */
	public final String stackTrace;
	
	/**
	 * Construct a new simple error message.
	 * @param message The simple message for user. 
	 * @param stackTrace The stack trace, which has to be reported.
	 */
	public ReErrorMessage(final String message, final String stackTrace) {
		this.message = message;
		this.stackTrace = stackTrace;
	}
	
	/**
	 * Construct a new error message with specified by Multex parameters and the stack trace. 
	 * 
	 * @param ex The exception, which has to be reported.
	 * @param stackTrace The stack trace, which has to be reported.
	 */
	public ReErrorMessage(final Throwable ex, final String stackTrace) {
		this(exceptionToString(ex), stackTrace);
	}
	
	/**
	 * Construct a new error mesage from exception an specified message with additional parameters. 
	 * @param ex The exception, which has to be reported.
	 * @return The resulting exception message with parameters.
	 */
	private static String exceptionToString(final Throwable ex){
		final ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, Locale.getDefault());
		final StringBuffer result = new StringBuffer();
        Msg.printMessages(result, ex, bundle);
		return result.toString();
	}
}
