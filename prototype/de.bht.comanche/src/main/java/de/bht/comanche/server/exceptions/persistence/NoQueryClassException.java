package de.bht.comanche.server.exceptions.persistence;

import de.bht.comanche.server.exceptions.PersistenceErrorMessage;
import de.bht.comanche.server.exceptions.PersistenceException;

public class NoQueryClassException extends PersistenceException {
	private static final long serialVersionUID = 8009364245587583748L;
	
	public NoQueryClassException() {
		super(PersistenceErrorMessage.NO_QUERY_CLASS);
	}
}