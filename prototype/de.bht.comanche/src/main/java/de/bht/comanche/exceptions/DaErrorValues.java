package de.bht.comanche.exceptions;

public enum DaErrorValues {
	OID_NOT_FOUND (2),
	OBJECT_NOT_FOUND (3),
	NO_QUERY_CLASS (4),
	NO_PERSISTENCE_CLASS (5),
	WRONG_ARGUMENT_TYPE (6), 
	WRONG_NUMBER_OF_ARGUMENTS (7),
	INVITE_NOT_FOUND(14);
	
	private final int responseCode;
	
	private DaErrorValues(final int errorCode) {
		    this.responseCode = errorCode;
    }
	
	public int getResponseCode() {
		return responseCode;
	}
}
