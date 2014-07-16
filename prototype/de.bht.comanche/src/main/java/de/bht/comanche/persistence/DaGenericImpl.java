package de.bht.comanche.persistence;

import java.util.Collection;
import java.util.List;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.exceptions.persistence.ArgumentCountException;
import de.bht.comanche.server.exceptions.persistence.ArgumentTypeException;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.NoQueryClassException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

public class DaGenericImpl<E> implements DaGeneric<E> {
	
	private Class<E> type;
	private Pool<E> pool;
	
	public DaGenericImpl(Class<E> type, Pool<E> pool) {
		this.type = type;
		this.pool = pool;
	}

	@Override
	public void save(E entity) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException {
		pool.save(entity);
	}

	@Override
	public void delete(E entity) throws TransactionRequiredException, IllegalArgumentException {
		pool.delete(entity);
	}

	@Override
	public E find(long id) throws NotFoundException, NoPersistentClassException, OidNotFoundException {
		return pool.find(type, id);
	}

	@Override
	public List<E> findAll() throws NoPersistentClassException {
		return pool.findAll(type);
	}

	@Override
	public List<E> findByField(String fieldName, Object fieldValue) throws NoPersistentClassException, NoQueryClassException, ArgumentCountException, ArgumentTypeException { 
		final String OBJECT_NAME = type.getSimpleName();
		String [] args = {
				fieldName,
				OBJECT_NAME,
				(String) fieldValue
		};
		return pool.findManyByQuery(type, "SELECT c FROM %2$s AS c WHERE c.%1$s LIKE '%3$s'", args);
	}

	@Override
	public List<E> findByWhere(String whereClause, Object... args) {
//		pool.findManyByQuery(type, "SELECT c.", i_args)
		return null;
	}

	@Override
	public List<E> findByExample(E example) {
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
	
	public Pool<E> getPool() {
		return this.pool;
	}
	
	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public void flush() {
		pool.flush();
	}

	@Override
	public E merge(E io_object) throws TransactionRequiredException, IllegalArgumentException {
		return pool.merge(io_object);
	}
}
