package de.bht.comanche.exceptions;


public class LgNoSurveyWithThisNameException extends LgException {

	private static final long serialVersionUID = -634651207924662285L;

	public LgNoSurveyWithThisNameException() {
		super(LgErrorValue.SURVEY_NOT_EXIST);
	}

}
