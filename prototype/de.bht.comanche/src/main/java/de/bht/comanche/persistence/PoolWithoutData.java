package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.DbObject;

public class PoolWithoutData implements Pool {

	@Override
	public void beginTransaction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTransaction(boolean success) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(DbObject io_object) throws EntityExistsException,
			IllegalArgumentException, TransactionRequiredException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(DbObject io_object) throws IllegalArgumentException,
			TransactionRequiredException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DbObject find(Class<? extends DbObject> i_persistentClass, Long i_oid)
			throws NoPersistentClassExc, OidNotFoundExc {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends DbObject> findAll(
			Class<? extends DbObject> i_persistentClass)
			throws NoPersistentClassExc {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends DbObject> findManyByQuery(
			Class<? extends DbObject> i_resultClass, String i_queryString,
			Object[] i_args) throws NoPersistentClassExc, NoQueryClassExc,
			ArgumentCountExc, ArgumentTypeExc {
		// TODO Auto-generated method stub
		return null;
	}

}
