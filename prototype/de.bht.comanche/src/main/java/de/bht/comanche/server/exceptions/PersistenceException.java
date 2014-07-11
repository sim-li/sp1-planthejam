package de.bht.comanche.server.exceptions;

public class PersistenceException extends PtjException {

	public PersistenceException(PersistenceErrorMessage error) {
		super(error.toString());
	}

}
