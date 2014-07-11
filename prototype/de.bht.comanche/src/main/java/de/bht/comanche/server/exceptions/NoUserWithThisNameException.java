package de.bht.comanche.server.exceptions;

public class NoUserWithThisNameException extends LogicException {
	private static final long serialVersionUID = 4664596846952133981L;

	public NoUserWithThisNameException() {
		super(LogicErrorMessage.WRONG_NAME);
	}
}
