package de.bht.comanche.rest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import de.bht.comanche.persistence.DaApplication;


public class RestService {
	private static final String USER_NAME = "user_name";

	public static String getUserName(HttpServletRequest request) {
		final HttpSession httpSession = request.getSession();
		return httpSession.getAttribute(USER_NAME).toString();
	}
	
	//for tests only, delete later
	public static String getDummyUserName(HttpServletRequest request) {
		final HttpSession httpSession = request.getSession();
		return httpSession.getAttribute("Alice").toString();
	}
	
	public static void setUserName(HttpServletRequest request, final String name) {
		final HttpSession httpSession = request.getSession();
		httpSession.setAttribute(USER_NAME, name);
	}
	
	//for tests only, delete later
	public static void setDummyUserName(HttpServletRequest request, final String name) {
		final HttpSession httpSession = request.getSession();
		httpSession.setAttribute(USER_NAME, "Alice");
	}
	
	public static void removeUserName(HttpServletRequest request) {
		final HttpSession httpSession = request.getSession();
		httpSession.removeAttribute(USER_NAME);
	}
}

