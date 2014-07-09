package de.bht.comanche.persistence;

import java.util.Collection;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.DbObject;

public class DaGenericImpl<E> implements DaGeneric<E> {
	
	private Class<E> type;
	private Pool pool;
	
	public DaGenericImpl(Class<E> type, Pool pool) {
		this.type = type;
		this.pool = pool;
	}

	@Override
	public void save(E entity) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException {
		pool.save((DbObject) entity);
		
	}

	@Override
	public void delete(E entity) throws TransactionRequiredException, IllegalArgumentException {
		pool.delete((DbObject) entity);
	}

	@Override
	public DbObject find(long id) throws NotFoundException, NoPersistentClassExc, OidNotFoundExc {
		// Quick Hack! Casting!
		return pool.find((Class<? extends DbObject>) type, id);
		
	}

	@Override
	public Collection<E> findAll() {
	}

	@Override
	public Collection<E> findByField(String fieldName, Object fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<E> findByWhere(String whereClause, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<E> findByExample(E example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beginTransaction() {
		pool.beginTransaction();
	}

	@Override
	public void endTransaction(boolean success) {
		pool.endTransaction(success);
	}

}
