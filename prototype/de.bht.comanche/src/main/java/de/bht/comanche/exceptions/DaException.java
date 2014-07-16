package de.bht.comanche.exceptions;

public class DaException extends PtjGlobalException {
	private static final long serialVersionUID = 617316525452380248L;

	public DaException(DaErrorValues msg) {
		super(msg.getResponseCode());
	}

}
