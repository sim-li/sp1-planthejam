package de.bht.comanche.server.exceptions;

public class LogicException extends PtjException {
	private static final long serialVersionUID = -2111591164406837128L;

	public LogicException(LogicErrorMessage msg) {
		super(msg.getResponseCode());
	}
	

}
