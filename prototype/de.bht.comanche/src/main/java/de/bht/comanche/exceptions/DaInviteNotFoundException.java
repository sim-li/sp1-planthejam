package de.bht.comanche.exceptions;


public class DaInviteNotFoundException extends DaException {

	private static final long serialVersionUID = -634651207924662285L;

	public DaInviteNotFoundException() {
		super(DaErrorValues.INVITE_NOT_FOUND);
	}

}
