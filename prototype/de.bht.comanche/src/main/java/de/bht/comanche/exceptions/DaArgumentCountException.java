package de.bht.comanche.exceptions;


public class DaArgumentCountException extends DaException {
	private static final long serialVersionUID = -7920913465247089343L;

	public DaArgumentCountException() {
		super(DaErrorValues.WRONG_NUMBER_OF_ARGUMENTS);
	}
}
