//package de.bht.comanche.logic;
//
//import de.bht.comanche.persistence.DaPool;
//
//public abstract class LgTransactionWithStackTrace<E> {
//	private final DaPool<E> pool;
//	private final boolean throwStackTrace;
//	private final boolean rollback;
//	
//	public LgTransactionWithStackTrace (DaPool<E> pool, boolean throwStackTrace, boolean rollback) {
//		this.pool = pool;
//		this.throwStackTrace = throwStackTrace;
//		this.rollback = rollback;
//	}
//	
//	public LgTransactionWithStackTrace (DaPool<E> pool, boolean throwStackTrace) {
//		this(pool, throwStackTrace, true);
//	}
//	
//	public LgTransactionWithStackTrace (DaPool<E> pool) {
//		this(pool, true, true);
//	}
//	
//	public boolean execute () {
//		pool.beginTransaction();
//		boolean success = false;
//		try {
//			executeWithThrows();
//			success = true;
//		} catch (Exception e) {
//			if (throwStackTrace) {
//				e.printStackTrace();
//			}
//		} finally {
//			if (rollback) {
//				pool.endTransaction(false);
//			} else {
//				pool.endTransaction(success);
//			}
//		}
//		return success;
//	}
//	
//	public void flush() {
//		pool.flush();
//	}
//	
//	public void forceTransactionEnd() {
//		pool.endTransaction(true);
//	}
//	
//	public void forceRestartTransaction() {
//		pool.endTransaction(true);
//		pool.beginTransaction();
//	}
//	
//	public abstract void executeWithThrows() throws Exception;
//}
//
