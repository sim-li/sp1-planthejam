package de.bht.comanche.exceptions;


public class LgSurveyWithThisNameExistsException extends LgException {

	private static final long serialVersionUID = 2015402307218594957L;
	
	public LgSurveyWithThisNameExistsException() {
		super(LgErrorValues.SURVEY_EXIST);
	}

}
