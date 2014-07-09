package de.bht.comanche.persistence;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.DbObject;
import javassist.NotFoundException;

/**
 * Must have constructor(Class&lt;E&gt; type)
 */
public interface DaGeneric<E> {	
	void save(E entity) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException;
    void delete(E entity) throws TransactionRequiredException, IllegalArgumentException;
    void beginTransaction();
    void endTransaction(boolean success);
    E find(long id) throws NotFoundException, NoPersistentClassExc, OidNotFoundExc;
    Collection<E> findAll();
    Collection<E> findByField(String fieldName, Object fieldValue);
    Collection<E> findByWhere(String whereClause, Object... args);
    Collection<E> findByExample(E example);
}
