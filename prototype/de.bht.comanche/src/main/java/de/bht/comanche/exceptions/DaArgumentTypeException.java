package de.bht.comanche.exceptions;


public class DaArgumentTypeException extends DaException {
	private static final long serialVersionUID = -758575384394400801L;
	
	public DaArgumentTypeException() {
		super(DaErrorValues.WRONG_ARGUMENT_TYPE);
	}
}
