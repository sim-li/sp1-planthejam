package de.bht.comanche.exceptions;

public enum LgErrorValue {
	WRONG_PASSWORD(8),
	WRONG_NAME(9),
	WRONG_ID(10),
	USER_EXIST(11),
	SURVEY_EXIST(12),
	MULTIPLE_USERS_WITH_NAME(13),
	SURVEY_NOT_EXIST(14);
	
	private final int responseCode;
	
	private LgErrorValue(final int errorCode) {
		    this.responseCode = errorCode;
    }
	
	public int getResponseCode() {
		return responseCode;
	}
}

