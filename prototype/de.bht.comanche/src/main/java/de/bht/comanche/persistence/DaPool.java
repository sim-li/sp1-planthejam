package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.EntityManager;

public interface DaPool {
	static final long createdOid = 0;
	static final long deletedOid = -1; 

	void insert(DaObject io_object);
	void save(DaObject io_object);
	boolean delete(DaObject io_object);

	<E extends DaObject> E find(Class<E> persistentClass, Long oid);
	<E extends DaObject> List<E> findAll(Class<E> persistentClass);
	<E extends DaObject> E findOneByKey(Class<E> persistentClass, String keyFieldName, Object keyFieldValue);
	<E extends DaObject> List<E> findManyByKey(Class<E> persistentClass, String keyFieldName, Object keyFieldValue);
	<E extends DaObject> List<E> findManyByQuery(Class<E> resultClass, Class queryClass, String queryString, Object[] args);
}
