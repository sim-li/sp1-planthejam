package de.bht.comanche.persistence;

import java.util.List;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.server.exceptions.persistence.ArgumentCountException;
import de.bht.comanche.server.exceptions.persistence.ArgumentTypeException;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.NoQueryClassException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

/**
 * Must have constructor(Class&lt;E&gt; type)
 */
public interface DaGeneric<E> {	
	void save(E entity) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException;
    void delete(E entity) throws TransactionRequiredException, IllegalArgumentException;
    void beginTransaction();
    void endTransaction(boolean success);
    E find(long id) throws NotFoundException, NoPersistentClassException, OidNotFoundException;
    List<E> findAll() throws NoPersistentClassException ;
    List<E> findByField(String fieldName, Object fieldValue)  throws NoPersistentClassException, NoQueryClassException, ArgumentCountException, ArgumentTypeException;
    List<E> findByWhere(String whereClause, Object... args);
    List<E> findByExample(E example);
    Pool<E> getPool();
    void setPool(Pool pool);
    void flush();
}
