package de.bht.comanche.logic;

import java.util.List;

import de.bht.comanche.exceptions.PtjGlobalException;
import de.bht.comanche.persistence.DaPool;
import de.bht.comanche.rest.ReResponseObject;

public abstract class LgTransaction<E> {
	private final DaPool<E> pool;
	private ReResponseObject<E> response;
	
	public LgTransaction (DaPool<E> pool) {
		this.pool = pool;
		response = new ReResponseObject<E>();
	}
	
	public void addToResponse(E data) {
		response.addData(data);
	}
	
	public void addAllToResponse(List<E> data) {
		response.addAll(data);
	}
	
	public ReResponseObject<E> execute () {
		pool.beginTransaction();
		boolean success = false;
		try {
			E objectFromDb = executeWithThrows();
			if (objectFromDb != null) {
				response.addData(objectFromDb);
			}
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
	
	public abstract E executeWithThrows() throws Exception;
}

