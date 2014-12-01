package de.bht.comanche.persistence;

import java.util.List;

public interface DaPool {
	
	static final long CREATED_OID = 0;
	static final long DELETED_OID = -1; 

	void insert(DaObject io_object);
	public <E extends DaObject> E save(DaObject io_object); 
	boolean delete(DaObject io_object);
	boolean contains(DaObject io_object);

	<E extends DaObject> E find(Class<E> persistentClass, long oid);
	<E extends DaObject> List<E> findAll(Class<E> persistentClass);
	<E extends DaObject> E findOneByKey(Class<E> persistentClass, String keyFieldName, Object keyFieldValue);
	<E extends DaObject> E findOneByTwoKeys(Class<E> persistentClass, String firstKeyFieldName,	Object firstKey, String secondKeyFieldName, Object secondKey);
	<E extends DaObject> List<E> findManyByKey(Class<E> persistentClass, String keyFieldName, Object keyFieldValue);
	<E extends DaObject> List<E> findManyByTwoKeys(Class<E> persistentClass, String firstKeyFieldName, Object firstKey, String secondKeyFieldName, Object secondKey);
	<E extends DaObject> List<E> findManyByQuery(Class<E> resultClass, Class<?> queryClass, String queryString, Object[] args);
}
