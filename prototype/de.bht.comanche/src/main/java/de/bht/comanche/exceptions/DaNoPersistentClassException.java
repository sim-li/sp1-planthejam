package de.bht.comanche.exceptions;


public class DaNoPersistentClassException extends DaException {
	private static final long serialVersionUID = -180317843353255837L;
	
	public DaNoPersistentClassException() {
		super(DaErrorValues.NO_PERSISTENCE_CLASS);
	}
}
