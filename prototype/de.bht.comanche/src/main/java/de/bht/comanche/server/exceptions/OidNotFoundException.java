package de.bht.comanche.server.exceptions;

public class OidNotFoundException extends PersistenceException {
	private static final long serialVersionUID = 2505210142705654274L;
	
	public OidNotFoundException() {
		super(PersistenceErrorMessage.OID_NOT_FOUND);
	}
}
