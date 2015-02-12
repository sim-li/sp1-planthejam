package de.bht.comanche.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This class provide a basic user authentication. Provides a way to identify a user across more than 
 * one page request or visit to a Web site and to store information about that user. 
 * @author Simon Lischka
 *
 */
public class RestService {
	/**
	 * Store the name to which the object is bound. Cannot be null.
	 */
	private static final String USER_NAME = "user_name";
	
	/**
	 * Get the name of current user. 
	 * @param request The request information from HTTP service.
	 * @return The name of current user.
	 */
	public static String getUserName(final HttpServletRequest request) {
		final HttpSession httpSession = request.getSession();
		return httpSession.getAttribute(USER_NAME).toString();
	}
	
	/**
	 * Sets the name of current user.
	 * @param request The request information from HTTP service.
	 * @param name The user name to be set.
	 */
	public static void setUserName(final HttpServletRequest request, final String name) {
		final HttpSession httpSession = request.getSession();
		httpSession.setAttribute(USER_NAME, name);
	}
	
	/**
	 * Removes the user name from current HTTP session.
	 * @param request The request information from HTTP service.
	 */
	public static void removeUserName(final HttpServletRequest request) {
		final HttpSession httpSession = request.getSession();
		httpSession.removeAttribute(USER_NAME);
	}
}
