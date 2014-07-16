package de.bht.comanche.server;

import java.util.List;

import de.bht.comanche.persistence.Pool;
import de.bht.comanche.server.exceptions.PtjException;

public abstract class TransactionWithList<E> {
	private final Pool<E> pool;
	private ResponseObject<E> response;
	
	public TransactionWithList (Pool<E> pool) {
		this.pool = pool;
		response = new ResponseObject<E>();
	}
	
	public ResponseObject<E> execute () {
		pool.beginTransaction();
		boolean success = false;
		try {
			List<E> objectsFromDb = executeWithThrows();
			response.addAll(objectsFromDb);
			success = true;
		} catch (PtjException ptjE) {
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

