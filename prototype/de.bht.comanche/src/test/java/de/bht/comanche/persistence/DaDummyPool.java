package de.bht.comanche.persistence;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.exceptions.DaArgumentCountException;
import de.bht.comanche.exceptions.DaArgumentTypeException;
import de.bht.comanche.exceptions.DaNoPersistentClassException;
import de.bht.comanche.exceptions.DaNoQueryClassException;
import de.bht.comanche.exceptions.DaOidNotFoundException;
import de.bht.comanche.logic.LgObject;
import de.bht.comanche.persistence.DaPool;

public class DaDummyPool<E> implements DaPool<E> {
	List<E> pool;
	
	public DaDummyPool() {
		pool = new LinkedList<E>();
	}

	@Override
	public void beginTransaction() {
		;
	}

	@Override
	public void endTransaction(boolean success) {
		;
	}

	@Override
	public void save(E io_object) throws EntityExistsException,
			IllegalArgumentException, TransactionRequiredException {
		pool.add(io_object);
	}

	@Override
	public void delete(E io_object) throws IllegalArgumentException,
			TransactionRequiredException {
		pool.remove(io_object);
	}

	@Override
	public E find(Class<E> i_persistentClass, Long i_oid)
			throws DaNoPersistentClassException, DaOidNotFoundException {
		for (E e : pool) {
			if (e instanceof LgObject) {
				LgObject obj = (LgObject) e;
				if (obj.getOid() == i_oid) {
					return e;
				}
			}
		}
		return null;
	}

	@Override
	public List<E> findAll(Class<E> i_persistentClass)
			throws DaNoPersistentClassException {
		List<E> result = new LinkedList<E>();
		for (E e : pool) {
			 if (e.getClass().equals(i_persistentClass)) {
				 result.add(e);
			 }
		}
		return result;
	}

	/*
	 * ARGS THAT ARE BEEING PROCESSED from i_args
	 * [0] fieldName,
	 * [1] OBJECT_NAME,
	 * [2] (String) fieldValue
	 */
	@Override
	public List<E> findManyByQuery(Class<E> i_resultClass,
			String i_queryString, Object[] i_args)
			throws DaNoPersistentClassException, DaNoQueryClassException,
			DaArgumentCountException, DaArgumentTypeException {
		if (!i_queryString.equals("SELECT c FROM %2$s AS c WHERE c.%1$s LIKE '%3$s'")) {
			throw new IllegalArgumentException();
		}
		List<E> result = new LinkedList<E>();
		for (E e : pool) {
			 if (e.getClass().equals(i_resultClass) 
					 && e.getClass().getSimpleName().equals(i_args[1]) 
					 && e instanceof LgObject) {
				 LgObject obj = (LgObject) e;
				 for (Field field : obj.getClass().getFields()) {
					try {
						if (field.getName().equals(i_args[0]) && field.get(i_args[0]) == i_args[2]) {
							result.add(e);
						}
					} catch (IllegalArgumentException exc1) {
						System.out.println("findManyByQuery:");
						System.out.println(">> TEST POOL got ILLEGAL ARGUMENT EXCEPTION (Probably accessing wrong field)!");
						exc1.printStackTrace();
					} catch (IllegalAccessException exc2) {
						System.out.println("findManyByQuery:");
						System.out.println(">> TEST POOL got ILLEGAL ACCCESS EXCEPTION!  (Probably accessing wrong field)");
						exc2.printStackTrace();
					}
				 }
			 }
		}
		return result;
	}

	@Override
	public void flush() {
		;
	}

	@Override
	public String getPersistenceUnitName() {
		return "ERROR> testPool: No persistence unit.";
	}

	@Override
	public E merge(E io_object) {
		// TODO Auto-generated method stub
		return null;
	}
}
