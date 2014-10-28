package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.persistence.DaPoolImpl.DaArgumentCountExc;
import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;

public interface DaPool<E> {
	public void beginTransaction();
	/**
	 * Should end the transaction with a commit if success was <code>true</code>, otherwise with a rollback.
	 */
	public void endTransaction(boolean success);
	public E save(E io_object);
	public E merge (E io_object);
	public void delete(E io_object);
	public E find(Class<E> i_persistentClass, Long i_oid) throws DaNoPersistentClassExc, DaOidNotFoundExc;
	public List<E> findAll(Class<E> i_persistentClass) throws DaNoPersistentClassExc;
	public List<E> findManyByQuery(Class<E> i_resultClass, String i_queryString, Object[] i_args) throws DaNoPersistentClassExc, DaArgumentCountExc;
	public void flush();
	public String getPersistenceUnitName();
}
