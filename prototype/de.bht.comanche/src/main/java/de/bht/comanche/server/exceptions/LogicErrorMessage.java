package de.bht.comanche.server.exceptions;

public enum LogicErrorMessage {
	WRONG_PASSWORD(8),
	WRONG_NAME(9),
	WRONG_ID(10),
	USER_EXIST(11);
	
	private final int responseCode;
	
	private LogicErrorMessage(final int errorCode) {
		    this.responseCode = errorCode;
    }
	
	public int getResponseCode() {
		return responseCode;
	}
}

