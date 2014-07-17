package de.bht.comanche.exceptions;


public class LgMultipleUsersWithThisNameException extends LgException {
	private static final long serialVersionUID = 6419230446334326516L;

	public LgMultipleUsersWithThisNameException() {
		super(LgErrorValues.MULTIPLE_USERS_WITH_NAME);
	}
}
