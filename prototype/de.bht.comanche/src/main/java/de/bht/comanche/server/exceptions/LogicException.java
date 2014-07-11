package de.bht.comanche.server.exceptions;

import org.postgresql.util.ServerErrorMessage;

public class LogicException extends PtjException {

	public LogicException(LogicErrorMessage wrongPassword) {
		super(wrongPassword.toString());
	}
	

}
