package webTest.exception;

public class DatabaseException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private final String errMsg;
	
	public DatabaseException(final String errMsg){
		super();
		this.errMsg = errMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}
	
	
}
