package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;
import de.bht.comanche.logic.DbObject;

public interface Pool {
	public void beginTransaction();
	/**
	 * Should end the transaction with a commit if success was <code>true</code>, otherwise with a rollback.
	 */
	public void endTransaction(boolean success);
	public void save(DbObject io_object) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException;
	public void delete(DbObject io_object) throws IllegalArgumentException, TransactionRequiredException;
	public DbObject find(Class<? extends DbObject> i_persistentClass, Long i_oid) throws NoPersistentClassExc, OidNotFoundExc;
	public List<? extends DbObject> findAll(Class<? extends DbObject> i_persistentClass) throws NoPersistentClassExc;
	public List<? extends DbObject> findManyByQuery(Class<? extends DbObject> i_resultClass, String i_queryString, Object[] i_args) throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc, ArgumentTypeExc;
}
