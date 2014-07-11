package de.bht.comanche.server.exceptions;

public enum LogicErrorMessage {
	WRONG_PASSWORD(8),
	WRONG_NAME(9);
	
	private final int responseCode;
	
	private LogicErrorMessage(final int errorCode) {
		    this.responseCode = errorCode;
    }
	
	public int getResponseCode() {
		return responseCode;
	}
}
