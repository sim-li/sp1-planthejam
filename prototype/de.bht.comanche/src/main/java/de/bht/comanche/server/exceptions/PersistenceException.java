package de.bht.comanche.server.exceptions;

public class PersistenceException extends PtjException {
	private static final long serialVersionUID = 617316525452380248L;

	public PersistenceException(PersistenceErrorMessage error) {
		super(error.toString());
	}

}
