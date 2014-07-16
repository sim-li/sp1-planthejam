package de.bht.comanche.exceptions;


public class DaNotFoundException extends DaException {
	private static final long serialVersionUID = -1293459381731596854L;

	public DaNotFoundException() {
		super(DaErrorValues.OBJECT_NOT_FOUND);
	}
}
