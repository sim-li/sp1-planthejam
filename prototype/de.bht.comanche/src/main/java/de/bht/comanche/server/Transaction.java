package de.bht.comanche.server;

import de.bht.comanche.logic.DbObject;
import de.bht.comanche.persistence.DaFactory;
import de.bht.comanche.persistence.Pool;
import de.bht.comanche.server.exceptions.NoUserWithThisNameExc;
import de.bht.comanche.server.exceptions.OidNotFoundException;
import de.bht.comanche.server.exceptions.UserWithThisNameExistsException;
import de.bht.comanche.server.exceptions.WrongPasswordException;

public abstract class Transaction<E> {
	private final Pool<E> pool;
	
	public Transaction (Pool<E> pool) {
		this.pool = pool;
	}
	
	public ResponseObject execute () {
		pool.beginTransaction();
		ResponseObject serverResponse = new ResponseObject();
		boolean success = false;
		try { // TODO : ROLL BACK
			DbObject objectFromDb = executeWithThrows();
			serverResponse.addData(objectFromDb);
			serverResponse.setSuccess(true);
		} catch (WrongPasswordException e) {
			serverResponse.addServerMessage("Wrong password");
			System.out.println("Wrong password");
//		} catch (NoUserWithThisNameExc e) {	
			serverResponse.addServerMessage("Wrong name");
			System.out.println("Wrong name");
		} catch (UserWithThisNameExistsException e) {
			serverResponse.addServerMessage("User with this name exists");
			System.out.println("User with this name exists");
		} catch (OidNotFoundException e) {
			serverResponse.addServerMessage("Oid not found");
			System.out.println("Oid not found");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CATCH DONE");
		} finally {
			System.out.println("FINALLY DONE");
			pool.endTransaction(success); 
		}
		return serverResponse;
	}
	
	public abstract DbObject executeWithThrows() throws Exception;
}

