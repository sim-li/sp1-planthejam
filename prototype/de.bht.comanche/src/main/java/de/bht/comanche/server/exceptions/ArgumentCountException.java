package de.bht.comanche.server.exceptions;

public class ArgumentCountException extends PersistenceException {
	private static final long serialVersionUID = -7920913465247089343L;

	public ArgumentCountException() {
		super(PersistenceErrorMessage.WRONG_NUMBER_OF_ARGUMENTS);
	}
}
