package de.bht.comanche.server.exceptions;

public enum LogicErrorMessage {
	WRONG_PASSWORD(8),
	WRONG_NAME(9),
	WRONG_ID(10),
	USER_EXIST(11),
<<<<<<< HEAD
	SURVEY_EXIST(12),
	SURVEY_NOT_EXIST(13);
	
=======
	SURVEY_EXIST(12), 
	MULTIPLE_USERS_WITH_NAME(13);
>>>>>>> 2f3308efb7603b36f75e3e04a1f53a5fbcf9b974
	
	private final int responseCode;
	
	private LogicErrorMessage(final int errorCode) {
		    this.responseCode = errorCode;
    }
	
	public int getResponseCode() {
		return responseCode;
	}
}

