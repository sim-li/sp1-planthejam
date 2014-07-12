package de.bht.comanche.server.exceptions.logic;

import de.bht.comanche.server.exceptions.LogicErrorMessage;
import de.bht.comanche.server.exceptions.LogicException;


public class UserWithThisNameExist extends LogicException {

	private static final long serialVersionUID = -3481383568588952305L;
	
	public UserWithThisNameExist() {
		super(LogicErrorMessage.USER_EXIST);
	}
}






