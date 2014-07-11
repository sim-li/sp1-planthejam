package de.bht.comanche.server.exceptions.persistence;

import de.bht.comanche.server.exceptions.PersistenceErrorMessage;
import de.bht.comanche.server.exceptions.PersistenceException;

public class NoPersistentClassException extends PersistenceException {
	private static final long serialVersionUID = -180317843353255837L;
	
	public NoPersistentClassException() {
		super(PersistenceErrorMessage.NO_PERSISTENCE_CLASS);
	}
}
