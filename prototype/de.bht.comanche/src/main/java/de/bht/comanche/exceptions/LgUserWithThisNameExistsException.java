package de.bht.comanche.exceptions;



public class LgUserWithThisNameExistsException extends LgException {

	private static final long serialVersionUID = -3481383568588952305L;
	
	public LgUserWithThisNameExistsException() {
		super(LgErrorValues.USER_EXIST);
	}
}






