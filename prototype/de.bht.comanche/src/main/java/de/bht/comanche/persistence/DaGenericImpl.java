package de.bht.comanche.persistence;

import java.util.Collection;
import java.util.List;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.exceptions.DaArgumentCountException;
import de.bht.comanche.exceptions.DaArgumentTypeException;
import de.bht.comanche.exceptions.DaNoPersistentClassException;
import de.bht.comanche.exceptions.DaNoQueryClassException;
import de.bht.comanche.exceptions.DaOidNotFoundException;
import de.bht.comanche.logic.LgUser;

public class DaGenericImpl<E> implements DaGeneric<E> {
	
	private Class<E> type;
	private DaPool<E> pool;
	
	public DaGenericImpl(Class<E> type, DaPool<E> pool) {
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
	public E find(long id) throws NotFoundException, DaNoPersistentClassException, DaOidNotFoundException {
		return pool.find(type, id);
	}

	@Override
	public List<E> findAll() throws DaNoPersistentClassException {
		return pool.findAll(type);
	}

	@Override
	public List<E> findByField(String fieldName, Object fieldValue) throws DaNoPersistentClassException, DaNoQueryClassException, DaArgumentCountException, DaArgumentTypeException { 
		final String OBJECT_NAME = type.getSimpleName();
		String [] args = {
				fieldName,
				OBJECT_NAME,
				(String) fieldValue
		};
		return pool.findManyByQuery(type, "SELECT c FROM %2$s AS c WHERE c.%1$s LIKE '%3$s'", args);
	}

	@Override
	public void beginTransaction() {
		pool.beginTransaction();
	}

	@Override
	public void endTransaction(boolean success) {
		pool.endTransaction(success);
	}
	
	public DaPool<E> getPool() {
		return this.pool;
	}
	
	public void setPool(DaPool pool) {
		this.pool = pool;
	}

	@Override
	public E update(E io_object) throws TransactionRequiredException, IllegalArgumentException {
		return pool.merge(io_object);
	}
}
