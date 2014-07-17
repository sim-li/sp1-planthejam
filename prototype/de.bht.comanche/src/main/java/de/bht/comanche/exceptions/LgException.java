package de.bht.comanche.exceptions;

public class LgException extends PtjGlobalException {
	private static final long serialVersionUID = -2111591164406837128L;

	public LgException(LgErrorValues msg) {
		super(msg.getResponseCode());
	}
	

}
