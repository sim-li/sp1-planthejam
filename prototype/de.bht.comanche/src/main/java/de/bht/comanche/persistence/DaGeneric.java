package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.persistence.DaPoolImpl.DaArgumentCountExc;
import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;

/**
 * Must have constructor(Class&lt;E&gt; type)
 */
public interface DaGeneric<E> {
	void save(E entity);
    void delete(E entity);
    E update(E io_object);
    void beginTransaction();
    void endTransaction(boolean success);
    E find(long id) throws DaNoPersistentClassExc, DaOidNotFoundExc;
    List<E> findAll() throws DaNoPersistentClassExc;
    List<E> findByField(String fieldName, Object fieldValue) throws DaNoPersistentClassExc, DaArgumentCountExc;
    DaPool getPool();
    void setPool(DaPool pool);
}
