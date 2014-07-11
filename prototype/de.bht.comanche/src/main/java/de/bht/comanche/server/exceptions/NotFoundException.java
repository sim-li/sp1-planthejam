package de.bht.comanche.server.exceptions;

public class NotFoundException extends PersistenceException {

	public NotFoundException() {
		super(PersistenceErrorMessage.OBJECT_NOT_FOUND);
	}
}
