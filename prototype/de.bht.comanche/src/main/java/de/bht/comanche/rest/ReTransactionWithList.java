package de.bht.comanche.rest;

import java.util.List;

import de.bht.comanche.exceptions.PtjGlobalException;
import de.bht.comanche.persistence.DaPool;

public abstract class ReTransactionWithList<E> {
	private final DaPool<E> pool;
	private ReResponseObject<E> response;
	
	public ReTransactionWithList (DaPool<E> pool) {
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
	
	public abstract List<E> executeWithThrows() throws Exception;
}

