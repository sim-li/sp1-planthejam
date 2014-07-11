package de.bht.comanche.server.exceptions;

public enum PersistenceErrorMessage {
	USER_EXISTS (1), 
	OID_NOT_FOUND (2),
	OBJECT_NOT_FOUND (3),
	NO_QUERY_CLASS (4),
	NO_PERSISTENCE_CLASS (5),
	WRONG_ARGUMENT_TYPE (6), 
	WRONG_NUMBER_OF_ARGUMENTS (7);
	
	private final int responseCode;
	
	private PersistenceErrorMessage(final int errorCode) {
		    this.responseCode = errorCode;
    }
	
	public int getResponseCode() {
		return responseCode;
	}
}
