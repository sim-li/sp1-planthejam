package de.bht.comanche.server.exceptions.logic;

import de.bht.comanche.server.exceptions.LogicErrorMessage;
import de.bht.comanche.server.exceptions.LogicException;

public class NoUserWithThisIdException extends LogicException {
	private static final long serialVersionUID = 5336024768757356136L;
	
	public NoUserWithThisIdException() {
		super(LogicErrorMessage.WRONG_ID);
	}
}
