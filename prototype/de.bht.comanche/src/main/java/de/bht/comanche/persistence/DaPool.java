package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.persistence.DaPoolImpl.DaArgumentCountExc;
import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
import de.bht.comanche.persistence.DaPoolImpl.EntityExistsExc;
import de.bht.comanche.persistence.DaPoolImpl.IllegalArgumentExc;
import de.bht.comanche.persistence.DaPoolImpl.TransactionRequiredExc;

public interface DaPool<E> {
	public void beginTransaction();
	/**
	 * Should end the transaction with a commit if success was <code>true</code>, otherwise with a rollback.
	 */
	public void endTransaction(boolean success);
	public void save(E io_object) throws EntityExistsExc, IllegalArgumentExc, TransactionRequiredExc;
	public E merge (E io_object) throws IllegalArgumentExc, TransactionRequiredExc;
	public void delete(E io_object) throws IllegalArgumentExc, TransactionRequiredExc;
	public E find(Class<E> i_persistentClass, Long i_oid) throws DaNoPersistentClassExc, DaOidNotFoundExc;
	public List<E> findAll(Class<E> i_persistentClass) throws DaNoPersistentClassExc;
	public List<E> findManyByQuery(Class<E> i_resultClass, String i_queryString, Object[] i_args) throws DaNoPersistentClassExc, /*DaNoQueryClassExc,*/ DaArgumentCountExc /*, DaArgumentTypeExc*/;
	public void flush();
	public String getPersistenceUnitName();
}
