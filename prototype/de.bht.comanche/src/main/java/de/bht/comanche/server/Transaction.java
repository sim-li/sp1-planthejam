package de.bht.comanche.server;

import java.util.List;

import de.bht.comanche.persistence.Pool;
import de.bht.comanche.server.exceptions.PtjException;

public abstract class Transaction<E> {
	private final Pool<E> pool;
	private ResponseObject<E> response;
	
	public Transaction (Pool<E> pool) {
		this.pool = pool;
		response = new ResponseObject<E>();
	}
	
	public void addToResponse(E data) {
		response.addData(data);
	}
	
	public void addAllToResponse(List<E> data) {
		response.addAll(data);
	}
	
	public ResponseObject<E> execute () {
		pool.beginTransaction();
		boolean success = false;
		try {
			E objectFromDb = executeWithThrows();
			if (objectFromDb != null) {
				response.addData(objectFromDb);
			}
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
	
	public abstract E executeWithThrows() throws Exception;
}

