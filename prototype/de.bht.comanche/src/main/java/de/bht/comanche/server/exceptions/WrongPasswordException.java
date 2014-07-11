package de.bht.comanche.server.exceptions;

public class WrongPasswordException extends LogicException {

	private static final long serialVersionUID = 1273895568189646082L;

	public WrongPasswordException(LogicErrorMessage exceptionMessage) {
		super(LogicErrorMessage.WRONG_PASSWORD);
	}
}
