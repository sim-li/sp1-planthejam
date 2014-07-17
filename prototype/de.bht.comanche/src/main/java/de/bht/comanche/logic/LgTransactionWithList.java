package de.bht.comanche.logic;

import java.util.List;

import de.bht.comanche.exceptions.PtjGlobalException;
import de.bht.comanche.persistence.DaPool;
import de.bht.comanche.rest.ReResponseObject;

public abstract class LgTransactionWithList<E> {
	private final DaPool<E> pool;
	private ReResponseObject<E> response;
	
	public LgTransactionWithList (DaPool<E> pool) {
		this.pool = pool;
		response = new ReResponseObject<E>();
	}
	
	public ReResponseObject<E> execute () {
		pool.beginTransaction();
		boolean success = false;
		try {
			List<E> objectsFromDb = executeWithThrows();
			response.addAll(objectsFromDb);
			success = true;
		} catch (PtjGlobalException ptjE) {
			response.setResponseCode(ptjE.getResponseCode());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.endTransaction(success); 
		}
		return response;
	}
	
	public void forceTransactionEnd() {
		pool.endTransaction(true);
	}
	
	public void forceRestartTransaction() {
		pool.endTransaction(true);
		pool.beginTransaction();
	}
	
	public abstract List<E> executeWithThrows() throws Exception;
}

