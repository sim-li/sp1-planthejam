package de.bht.comanche.rest;

public abstract class ReLowLevelTransaction {
	private final boolean throwStackTrace;
	
	public ReLowLevelTransaction (boolean throwStackTrace) {
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

