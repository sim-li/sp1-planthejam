package de.bht.comanche.server.exceptions;

public class PtjException extends Exception {
	private int responseCode;
	
	public PtjException(int responseCode) {
		this.responseCode = responseCode;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
}
