package de.bht.comanche.server.exceptions.logic;

import de.bht.comanche.server.exceptions.LogicErrorMessage;
import de.bht.comanche.server.exceptions.LogicException;

public class MultipleUsersWithThisNameException extends LogicException {
	private static final long serialVersionUID = 6419230446334326516L;

	public MultipleUsersWithThisNameException() {
		super(LogicErrorMessage.MULTIPLE_USERS_WITH_NAME);
	}
}
