package de.bht.comanche.server;

import de.bht.comanche.logic.DbObject;
import de.bht.comanche.persistence.OidNotFoundExc;
import de.bht.comanche.persistence.Pool;
import de.bht.comanche.persistence.PoolImpl;

public abstract class Transaction<E> {
	
	public ResponseObject execute () {
//		Pool pool = new PoolImpl<E>();  // FIXME
//		pool.beginTransaction();
		
		ResponseObject serverResponse = new ResponseObject();
		serverResponse.setSuccess(false);
		
//		boolean success = false; // determines how to end the transaction
		try {
			DbObject objectFromDb = executeWithThrows();
			serverResponse.addData(objectFromDb);
			
			serverResponse.setSuccess(true);
//			success = true;
			System.out.println("TRY DONE");
		} catch (WrongPasswordExc e) {
			serverResponse.addServerMessage("Wrong password");
			System.out.println("Wrong password");
		} catch (NoUserWithThisNameExc e) {	
			serverResponse.addServerMessage("Wrong name");
			System.out.println("Wrong name");
		} catch (UserWithThisNameExistsExc e) {
			serverResponse.addServerMessage("User with this name exists");
			System.out.println("User with this name exists");
		} catch (OidNotFoundExc e) {
			serverResponse.addServerMessage("Oid not found");
			System.out.println("Oid not found");
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println("CATCH DONE");
		} finally {
			System.out.println("FINALLY DONE");
//			pool.endTransaction(success);
		}
		return serverResponse;
	}
	
	public abstract DbObject executeWithThrows() throws Exception;
}

