package de.bht.comanche.server.exceptions.persistence;

import de.bht.comanche.server.exceptions.PersistenceErrorMessage;
import de.bht.comanche.server.exceptions.PersistenceException;

public class NotFoundException extends PersistenceException {
	private static final long serialVersionUID = -1293459381731596854L;

	public NotFoundException() {
		super(PersistenceErrorMessage.OBJECT_NOT_FOUND);
	}
}
