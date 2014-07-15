package de.bht.comanche.testresources.persistence;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.DbObject;
import de.bht.comanche.persistence.Pool;
import de.bht.comanche.server.exceptions.persistence.ArgumentCountException;
import de.bht.comanche.server.exceptions.persistence.ArgumentTypeException;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.NoQueryClassException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

public class PoolTesting<E> implements Pool<E> {
	List<E> pool;
	
	public PoolTesting() {
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
			throws NoPersistentClassException, OidNotFoundException {
		for (E e : pool) {
			if (e instanceof DbObject) {
				DbObject obj = (DbObject) e;
				if (obj.getOid() == i_oid) {
					return e;
				}
			}
		}
		return null;
	}

	@Override
	public List<E> findAll(Class<E> i_persistentClass)
			throws NoPersistentClassException {
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
			throws NoPersistentClassException, NoQueryClassException,
			ArgumentCountException, ArgumentTypeException {
		if (!i_queryString.equals("SELECT c FROM %2$s AS c WHERE c.%1$s LIKE '%3$s'")) {
			throw new IllegalArgumentException();
		}
		List<E> result = new LinkedList<E>();
		for (E e : pool) {
			 if (e.getClass().equals(i_resultClass) 
					 && e.getClass().getSimpleName().equals(i_args[1]) 
					 && e instanceof DbObject) {
				 DbObject obj = (DbObject) e;
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
}
