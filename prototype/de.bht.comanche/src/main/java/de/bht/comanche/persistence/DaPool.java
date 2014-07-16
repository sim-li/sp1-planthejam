package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.exceptions.DaArgumentCountException;
import de.bht.comanche.exceptions.DaArgumentTypeException;
import de.bht.comanche.exceptions.DaNoPersistentClassException;
import de.bht.comanche.exceptions.DaNoQueryClassException;
import de.bht.comanche.exceptions.DaOidNotFoundException;
import de.bht.comanche.logic.LgUser;

public interface DaPool<E> {
	public void beginTransaction();
	/**
	 * Should end the transaction with a commit if success was <code>true</code>, otherwise with a rollback.
	 */
	public void endTransaction(boolean success);
	public void save(E io_object) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException;
	public E merge (E io_object) throws IllegalArgumentException, TransactionRequiredException;
	public void delete(E io_object) throws IllegalArgumentException, TransactionRequiredException;
	public E find(Class<E> i_persistentClass, Long i_oid) throws DaNoPersistentClassException, DaOidNotFoundException;
	public List<E> findAll(Class<E> i_persistentClass) throws DaNoPersistentClassException;
	public List<E> findManyByQuery(Class<E> i_resultClass, String i_queryString, Object[] i_args) throws DaNoPersistentClassException, DaNoQueryClassException, DaArgumentCountException, DaArgumentTypeException;
	public void flush();
	public String getPersistenceUnitName();
}
