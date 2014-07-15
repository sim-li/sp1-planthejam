package de.bht.comanche.testresources.server;

public abstract class LowLevelTransaction {
	private final boolean throwStackTrace;
	
	public LowLevelTransaction (boolean throwStackTrace) {
		this.throwStackTrace = throwStackTrace;
	}
	
	public boolean execute () {
		boolean success = false;
		try {
			executeWithThrows();
			success = true;
		} catch (Exception e) {
			if (throwStackTrace) {
				e.printStackTrace();
			}
		} 
		return success;
	}
	
	public abstract void executeWithThrows() throws Exception;
}

