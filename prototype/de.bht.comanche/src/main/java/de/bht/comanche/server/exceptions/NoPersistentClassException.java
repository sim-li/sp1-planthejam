package de.bht.comanche.server.exceptions;

public class NoPersistentClassException extends PersistenceException {

	public NoPersistentClassException() {
		super(PersistenceErrorMessage.NO_PERSISTENCE_CLASS);
	}

	private static final long serialVersionUID = -180317843353255837L;

}
