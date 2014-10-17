package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
import de.bht.comanche.persistence.DaPoolImpl.EntityExistsExc;
import de.bht.comanche.persistence.DaPoolImpl.IllegalArgumentExc;
import de.bht.comanche.persistence.DaPoolImpl.TransactionRequiredExc;

/**
 * Must have constructor(Class&lt;E&gt; type)
 */
public interface DaGeneric<E> {
	void save(E entity) throws EntityExistsExc, TransactionRequiredExc, IllegalArgumentExc;
    void delete(E entity) throws TransactionRequiredExc, IllegalArgumentExc;
    void beginTransaction();
    void endTransaction(boolean success);
    E find(long id) throws DaNoPersistentClassExc, DaOidNotFoundExc;
    List<E> findAll() throws DaNoPersistentClassExc;
    List<E> findByField(String fieldName, Object fieldValue);
    DaPool getPool();
    void setPool(DaPool pool);
    E update(E io_object) throws TransactionRequiredExc, IllegalArgumentExc;
}
