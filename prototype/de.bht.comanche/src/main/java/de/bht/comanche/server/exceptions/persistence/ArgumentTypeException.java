package de.bht.comanche.server.exceptions.persistence;

import de.bht.comanche.server.exceptions.PersistenceErrorMessage;
import de.bht.comanche.server.exceptions.PersistenceException;

public class ArgumentTypeException extends PersistenceException {
	private static final long serialVersionUID = -758575384394400801L;
	
	public ArgumentTypeException() {
		super(PersistenceErrorMessage.WRONG_ARGUMENT_TYPE);
	}
}
