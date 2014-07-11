package de.bht.comanche.server.exceptions;

public class NoPersistentClassException extends PersistenceException {
	private static final long serialVersionUID = -180317843353255837L;
	
	public NoPersistentClassException() {
		super(PersistenceErrorMessage.NO_PERSISTENCE_CLASS);
	}
}
