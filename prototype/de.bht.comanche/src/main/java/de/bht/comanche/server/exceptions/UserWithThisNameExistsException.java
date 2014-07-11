package de.bht.comanche.server.exceptions;

public class UserWithThisNameExistsException extends PersistenceException  {
	private static final long serialVersionUID = 9064007080476907318L;

	public UserWithThisNameExistsException(LogicErrorMessage exceptionMessage) {
		super(PersistenceErrorMessage.USER_EXISTS);
	}
}
