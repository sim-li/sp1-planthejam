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
		ResponseObject serverResponse = new ResponseObject();
		boolean success = false;
		try {
			DbObject objectFromDb = executeWithThrows();
			serverResponse.addData(objectFromDb);
			serverResponse.setSuccess(true);
		} catch (PtjException ptjE) {
			success = false;
			serverResponse.addServerMessage(ptjE.getMessage());
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		} finally {
			pool.endTransaction(success); 
		}
		return serverResponse;
	}
	
	public abstract DbObject executeWithThrows() throws Exception;
}

