package de.bht.comanche.persistence;

import java.util.Collection;

import javassist.NotFoundException;

public class DaGenericImpl<E> implements DaGeneric<E> {
	
	private Class<E> type;
	private Pool pool;
	
	public DaGenericImpl(Class<E> type, Pool pool) {
		this.type = type;
		this.pool = pool;
	}

	@Override
	public void save(E entity) {
		System.out.println("I got called.");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(E entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E find(long id) throws NotFoundException {
		//return pool.find(i_persistentClass, i_oid)
		return null;
	}

	@Override
	public Collection<E> findAll() {
		// TODO Auto-generated method stub
		return null;
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
