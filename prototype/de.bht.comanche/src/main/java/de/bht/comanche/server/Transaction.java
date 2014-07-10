package de.bht.comanche.server;

import de.bht.comanche.logic.DbObject;
import de.bht.comanche.persistence.Pool;
import de.bht.comanche.persistence.PoolImpl;

public abstract class Transaction<E> {
	
	public ResponseObject execute () {
//		Pool pool = PoolImpl.getInstance();  // FIXME
		ResponseObject serverResponse = null;
		boolean success = false;
		try {
			serverResponse = new ResponseObject();
			DbObject objectFromDb = executeWithThrows();
			serverResponse.addData(objectFromDb);
			serverResponse.setSuccess(true);
			System.out.println("TRY DONE");
		} catch (Exception e) {
			e.printStackTrace();
			serverResponse.setSuccess(false);
			if (e instanceof WrongPasswordExc) {
				serverResponse.addServerMessage("Wrong Password");
			}
			System.out.println("CATCH DONE");
		} finally {
			System.out.println("FINALLY DONE");
//			pool.endTransaction(success);
		}
		return serverResponse;
	}
	
	public abstract DbObject executeWithThrows() throws Exception;
}

