package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.exceptions.persistence.ArgumentCountException;
import de.bht.comanche.server.exceptions.persistence.ArgumentTypeException;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.NoQueryClassException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

public interface Pool<E> {
	public void beginTransaction();
	/**
	 * Should end the transaction with a commit if success was <code>true</code>, otherwise with a rollback.
	 */
	public void endTransaction(boolean success);
	public void save(E io_object) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException;
	public void delete(E io_object) throws IllegalArgumentException, TransactionRequiredException;
	public E find(Class<E> i_persistentClass, Long i_oid) throws NoPersistentClassException, OidNotFoundException;
	public List<E> findAll(Class<E> i_persistentClass) throws NoPersistentClassException;
	public List<E> findManyByQuery(Class<E> i_resultClass, String i_queryString, Object[] i_args) throws NoPersistentClassException, NoQueryClassException, ArgumentCountException, ArgumentTypeException;
}
