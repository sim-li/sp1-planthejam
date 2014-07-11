package de.bht.comanche.server.exceptions.logic;

import de.bht.comanche.server.exceptions.LogicErrorMessage;
import de.bht.comanche.server.exceptions.LogicException;

public class NoUserWithThisNameException extends LogicException {
	private static final long serialVersionUID = 4664596846952133981L;

	public NoUserWithThisNameException() {
		super(LogicErrorMessage.WRONG_NAME);
	}
}
