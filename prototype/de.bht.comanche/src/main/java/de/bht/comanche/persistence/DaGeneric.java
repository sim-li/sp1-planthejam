package de.bht.comanche.persistence;

import java.util.Collection;

import javassist.NotFoundException;

/**
 * Must have constructor(Class&lt;E&gt; type)
 */
public interface DaGeneric<E> {	
	void save(E entity);
    void delete(E entity);
    void beginTransaction();
    void endTransaction(boolean success);
    E find(long id) throws NotFoundException;
    Collection<E> findAll();
    Collection<E> findByField(String fieldName, Object fieldValue);
    Collection<E> findByWhere(String whereClause, Object... args);
    Collection<E> findByExample(E example);
}
