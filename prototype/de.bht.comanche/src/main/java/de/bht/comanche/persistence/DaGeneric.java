package de.bht.comanche.persistence;

import java.util.List;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.exceptions.DaArgumentCountException;
import de.bht.comanche.exceptions.DaArgumentTypeException;
import de.bht.comanche.exceptions.DaNoPersistentClassException;
import de.bht.comanche.exceptions.DaNoQueryClassException;
import de.bht.comanche.exceptions.DaOidNotFoundException;
import de.bht.comanche.logic.LgUser;

/**
 * Must have constructor(Class&lt;E&gt; type)
 */
public interface DaGeneric<E> {	
	void save(E entity) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException;
    void delete(E entity) throws TransactionRequiredException, IllegalArgumentException;
    void beginTransaction();
    void endTransaction(boolean success);
    E find(long id) throws NotFoundException, DaNoPersistentClassException, DaOidNotFoundException;
    List<E> findAll() throws DaNoPersistentClassException ;
    List<E> findByField(String fieldName, Object fieldValue)  throws DaNoPersistentClassException, DaNoQueryClassException, DaArgumentCountException, DaArgumentTypeException;
    DaPool getPool();
    void setPool(DaPool pool);
    E update(E io_object) throws TransactionRequiredException, IllegalArgumentException;
}
