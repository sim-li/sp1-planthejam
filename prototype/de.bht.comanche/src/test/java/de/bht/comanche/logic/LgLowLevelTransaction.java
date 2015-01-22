//package de.bht.comanche.logic;
//
//public abstract class LgLowLevelTransaction {
//	private final boolean throwStackTrace;
//	
//	public LgLowLevelTransaction (boolean throwStackTrace) {
//		this.throwStackTrace = throwStackTrace;
//	}
//	
//	public boolean execute () {
//		boolean success = false;
//		try {
//			executeWithThrows();
//			success = true;
//		} catch (Exception e) {
//			if (throwStackTrace) {
//				e.printStackTrace();
//			}
//		} 
//		return success;
//	}
//	
//	public abstract void executeWithThrows() throws Exception;
//}
