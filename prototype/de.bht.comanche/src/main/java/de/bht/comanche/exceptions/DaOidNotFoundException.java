package de.bht.comanche.exceptions;


public class DaOidNotFoundException extends DaException {
	private static final long serialVersionUID = 2505210142705654274L;
	
	public DaOidNotFoundException() {
		super(DaErrorValues.OID_NOT_FOUND);
	}
}
