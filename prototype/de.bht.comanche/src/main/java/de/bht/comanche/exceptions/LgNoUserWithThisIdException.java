package de.bht.comanche.exceptions;


public class LgNoUserWithThisIdException extends LgException {
	private static final long serialVersionUID = 5336024768757356136L;
	
	public LgNoUserWithThisIdException() {
		super(LgErrorValue.WRONG_ID);
	}
}
