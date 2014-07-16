package de.bht.comanche.server.exceptions.logic;

import de.bht.comanche.server.exceptions.LogicErrorMessage;
import de.bht.comanche.server.exceptions.LogicException;
import de.bht.comanche.server.exceptions.PtjException;

public class NoSurveyWithThisNameException extends LogicException {

	private static final long serialVersionUID = -634651207924662285L;

	public NoSurveyWithThisNameException() {
		super(LogicErrorMessage.SURVEY_NOT_EXIST);
	}

}
