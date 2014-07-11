package de.bht.comanche.persistence;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.DbObject;
import de.bht.comanche.server.exceptions.ArgumentCountException;
import de.bht.comanche.server.exceptions.ArgumentTypeException;
import de.bht.comanche.server.exceptions.NoPersistentClassException;
import de.bht.comanche.server.exceptions.NoQueryClassException;
import de.bht.comanche.server.exceptions.OidNotFoundException;
import javassist.NotFoundException;

/**
 * Must have constructor(Class&lt;E&gt; type)
 */
public interface DaGeneric<E> {	
	void save(E entity) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException;
    void delete(E entity) throws TransactionRequiredException, IllegalArgumentException;
    void beginTransaction();
    void endTransaction(boolean success);
    E find(long id) throws NotFoundException, NoPersistentClassException, OidNotFoundException;
    Collection<E> findAll() throws NoPersistentClassException ;
    Collection<E> findByField(String fieldName, Object fieldValue)  throws NoPersistentClassException, NoQueryClassException, ArgumentCountException, ArgumentTypeException;
    Collection<E> findByWhere(String whereClause, Object... args);
    Collection<E> findByExample(E example);
    Pool<E> getPool();
}
