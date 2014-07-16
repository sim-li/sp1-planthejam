package de.bht.comanche.exceptions;


public class LgNoUserWithThisNameException extends LgException {
	private static final long serialVersionUID = 4664596846952133981L;

	public LgNoUserWithThisNameException() {
		super(LgErrorValue.WRONG_NAME);
	}
}
