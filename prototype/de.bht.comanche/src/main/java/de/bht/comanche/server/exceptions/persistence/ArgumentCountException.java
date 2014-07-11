package de.bht.comanche.server.exceptions.persistence;

import de.bht.comanche.server.exceptions.PersistenceErrorMessage;
import de.bht.comanche.server.exceptions.PersistenceException;

public class ArgumentCountException extends PersistenceException {
	private static final long serialVersionUID = -7920913465247089343L;

	public ArgumentCountException() {
		super(PersistenceErrorMessage.WRONG_NUMBER_OF_ARGUMENTS);
	}
}
