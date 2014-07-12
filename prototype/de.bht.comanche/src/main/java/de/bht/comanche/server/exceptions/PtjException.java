package de.bht.comanche.server.exceptions;

public class PtjException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2156826884300882012L;
	private int responseCode;
	
	public PtjException(int responseCode) {
		this.responseCode = responseCode;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
}
