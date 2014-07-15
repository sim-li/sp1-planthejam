package de.bht.comanche.server.exceptions.logic;

import de.bht.comanche.server.exceptions.LogicErrorMessage;
import de.bht.comanche.server.exceptions.LogicException;

public class SurveyWithThisNameExistsException extends LogicException {

	private static final long serialVersionUID = 2015402307218594957L;
	
	public SurveyWithThisNameExistsException() {
		super(LogicErrorMessage.SURVEY_EXIST);
	}

}
