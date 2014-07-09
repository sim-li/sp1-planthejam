package de.bht.comanche.server;

import javax.ws.rs.WebApplicationException;

import de.bht.comanche.logic.DbObject;

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
		} catch (WrongPasswordExc e) {
			serverResponse.addServerMessage("Wrong Password");
			System.out.println("Wrong Password");
		} catch (Exception e) {
			e.printStackTrace();
			serverResponse.setSuccess(false);
			
			System.out.println("CATCH DONE");
		} finally {
			System.out.println("FINALLY DONE");
//			pool.endTransaction(success);
		}
		return serverResponse;
	}
	
	public abstract DbObject executeWithThrows() throws Exception;
}

