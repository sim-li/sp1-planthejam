package de.bht.comanche.exceptions;


public class DaNoQueryClassException extends DaException {
	private static final long serialVersionUID = 8009364245587583748L;
	
	public DaNoQueryClassException() {
		super(DaErrorValues.NO_QUERY_CLASS);
	}
}
