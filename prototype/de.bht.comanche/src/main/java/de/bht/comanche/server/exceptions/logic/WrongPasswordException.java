package de.bht.comanche.server.exceptions.logic;

import de.bht.comanche.server.exceptions.LogicErrorMessage;
import de.bht.comanche.server.exceptions.LogicException;

public class WrongPasswordException extends LogicException {
	private static final long serialVersionUID = 1273895568189646082L;

	public WrongPasswordException() {
		super(LogicErrorMessage.WRONG_PASSWORD);
	}
}
