package de.bht.comanche.exceptions;

public class PtjGlobalException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2156826884300882012L;
	private int responseCode;
	
	public PtjGlobalException(int responseCode) {
		this.responseCode = responseCode;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
}
