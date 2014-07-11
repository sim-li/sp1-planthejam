package de.bht.comanche.server;

import de.bht.comanche.logic.DbObject;
import de.bht.comanche.persistence.Pool;
import de.bht.comanche.server.exceptions.PtjException;

public abstract class Transaction<E> {
	private final Pool<E> pool;
	
	public Transaction (Pool<E> pool) {
		this.pool = pool;
	}
	
	public ResponseObject execute () {
		pool.beginTransaction();
		ResponseObject response = new ResponseObject();
		boolean success = false;
		try {
			DbObject objectFromDb = executeWithThrows();
			response.addData(objectFromDb);
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
	
	public abstract DbObject executeWithThrows() throws Exception;
}

