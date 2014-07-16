package de.bht.comanche.server.exceptions.logic;

import de.bht.comanche.server.exceptions.LogicErrorMessage;
import de.bht.comanche.server.exceptions.LogicException;

public class MultipleUsersWithThisNameException extends LogicException {

	public MultipleUsersWithThisNameException() {
		super(LogicErrorMessage.WRONG_NAME);
	}
}
