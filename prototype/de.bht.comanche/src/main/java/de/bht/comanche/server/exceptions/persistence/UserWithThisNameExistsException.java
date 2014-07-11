package de.bht.comanche.server.exceptions.persistence;

import de.bht.comanche.server.exceptions.PersistenceErrorMessage;
import de.bht.comanche.server.exceptions.PersistenceException;

public class UserWithThisNameExistsException extends PersistenceException  {
	private static final long serialVersionUID = 9064007080476907318L;

	public UserWithThisNameExistsException() {
		super(PersistenceErrorMessage.USER_EXISTS);
	}
}
