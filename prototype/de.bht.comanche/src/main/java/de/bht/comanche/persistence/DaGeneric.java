package de.bht.comanche.persistence;

import java.util.Collection;

import javassist.NotFoundException;

public interface DaGeneric<E> extends DbObject {
	
	// Must have constructor(Class<E> type)
	
	void save(E entity);
    void delete(E entity);
    E find(long id) throws NotFoundException;
    Collection<E> findAll();
    Collection<E> findByField(String fieldName, Object fieldValue);
    Collection<E> findByWhere(String whereClause, Object... args); // ???
    Collection<E> findByExample(E example);
}
