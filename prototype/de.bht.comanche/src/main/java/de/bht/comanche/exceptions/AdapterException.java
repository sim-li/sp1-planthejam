package de.bht.comanche.exceptions;


public class AdapterException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PtjGlobalException ptj;
	
	/**
	 *HTTP status of the response 
	*/
	int status;
	
	/**
	 * user message
	 */
	String errorMessage;

	/**
	 * stack Trace
	 */
	private String stackTrace;
	
	
	public AdapterException(int status, String errorMessage, String stackTrace) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.stackTrace = stackTrace;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}

	public String gStackTrace() {
		return stackTrace;
	}
	
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}
