package de.bht.comanche.server.exceptions;

public class NotFoundException extends PersistenceException {
	private static final long serialVersionUID = -1293459381731596854L;

	public NotFoundException() {
		super(PersistenceErrorMessage.OBJECT_NOT_FOUND);
	}
}
