package de.bht.comanche.server;

import de.bht.comanche.logic.DbObject;
import de.bht.comanche.persistence.Pool;
import de.bht.comanche.persistence.PoolImpl;

public abstract class Transaction<E> {
	
	public ResponseObject execute () {
		Pool pool = PoolImpl.getInstance();
		ResponseObject serverResponse = null;
		boolean success = false;
		try {
			serverResponse = new ResponseObject();
			DbObject objectFromDb = executeWithThrows();
			serverResponse.addData(objectFromDb);
			serverResponse.setSuccess(true);
		} catch (Exception e) {
			serverResponse.setSuccess(false);
			if (e instanceof WrongPasswordExc) {
				serverResponse.addServerMessage("Wrong Password");
			}
		} finally {
			pool.endTransaction(success);
		}
		return serverResponse;
	}
	
	public abstract DbObject executeWithThrows() throws Exception;
}

