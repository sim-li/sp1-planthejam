package de.bht.comanche.exceptions;


public class LgWrongPasswordException extends LgException {
	private static final long serialVersionUID = 1273895568189646082L;

	public LgWrongPasswordException() {
		super(LgErrorValue.WRONG_PASSWORD);
	}
}
