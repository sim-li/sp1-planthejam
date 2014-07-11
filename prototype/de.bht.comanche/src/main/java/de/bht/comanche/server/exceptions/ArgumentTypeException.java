package de.bht.comanche.server.exceptions;

public class ArgumentTypeException extends PersistenceException {
	private static final long serialVersionUID = -758575384394400801L;
	
	public ArgumentTypeException() {
		super(error);
	}
}
