package de.bht.comanche.persistence;

import java.util.List;
import de.bht.comanche.persistence.DaHibernateJpaPool.DaFindOneByKeyExc;

/**
 * Interface for <code>pool</code> implementation of a concrete persistence provider.
 * 
 * Provides basic data access methods for use with <code>DaObject</code>s. 
 * 
 * @author Simon Lischka
 *
 */
public interface DaPool {
	
	/**
	 * Object id for non persistent object
	 */
	static final long CREATED_OID = 0;
	
	/**
	 * Object id for deleted object
	 */
	static final long DELETED_OID = -1; 

	/**
	 * Saves a new entity to the pool. An existent object will
	 * not be updated. 
	 * 
	 * Entity will be managed after operation.
	 * 
	 * @param io_object Entity to be persisted
	 */
	void insert(DaObject io_object);
	
	/**
	 * Saves an entity to the pool. An existent object will
	 * be updated.
	 * 
	 * A managed copy of the entity will be returned.
	 * 
	 * @param io_object Entity to be persisted
	 * @return Managed copy of the entity
	 */
	public <E extends DaObject> E save(DaObject io_object); 
	
	/**
	 * Deletes the entity from the pool. 
	 * 
	 * @param io_object Entity to be deleted
	 * @return True on success
	 */
	boolean delete(DaObject io_object);
	
	/**
	 * Returns true if entity belongs to persistence context.
	 * 
	 * @param io_object
	 * @return True if entity in persistence context 
	 */
	boolean contains(DaObject io_object);

	/**
	 * Searches DB for entity with given object id. 
	 * 
	 * @param persistentClass Class of the entity to be searched for
	 * @param oid Object id of entity 
	 * @return Entity from search
	 */
	<E extends DaObject> E find(Class<E> persistentClass, long oid);
	
	/**
	 * Returns all entities of the the specified type.
	 * 
	 * @param persistentClass Class of the entities to be retrieved
	 * @return List of entities from search
	 */
	<E extends DaObject> List<E> findAll(Class<E> persistentClass);
	
	/**
	 * Searches for an entity by key/value pair
	 * 
	 * @param persistentClass  Class of the entity to be searched for
	 * @param keyFieldName Key name of entity field
	 * @param keyFieldValue Value name of entity field
	 * @return Entity from search
	 */
	<E extends DaObject> E findOneByKey(Class<E> persistentClass, String keyFieldName, Object keyFieldValue) throws DaFindOneByKeyExc;
	
	/**
	 * Searches for an entity by two key/value pairs 
	 * 
	 * @param persistentClass  Class of the entity to be searched for
	 * @param firstKeyFieldName First key name of entity field
	 * @param firstKey First value name of entity field
	 * @param secondKeyFieldName Second key name of entity field
	 * @param secondKey Second value name of entity field
	 * @return Entity from search
	 */
	<E extends DaObject> E findOneByTwoKeys(Class<E> persistentClass, String firstKeyFieldName,	Object firstKey, String secondKeyFieldName, Object secondKey);
	
	
	/**
	 * Searches for various entities by key/value pair
	 * 
	 * @param persistentClass Class of the entity to be searched for
	 * @param keyFieldName Key name of entity field
	 * @param keyFieldValue Value name of entity field
	 * @return List of entities form search
	 */
	<E extends DaObject> List<E> findManyByKey(Class<E> persistentClass, String keyFieldName, Object keyFieldValue);
	
	/**
	 * Searches for various entities by two key/value pairs 
	 * 
	 * @param persistentClass Class of the entity to be searched for
	 * @param firstKeyFieldName First key name of entity field
	 * @param firstKey  First value name of entity field
	 * @param secondKeyFieldName Second key name of entity field
	 * @param secondKey y Second value name of entity field
	 * @return List of entities form search
	 */
	<E extends DaObject> List<E> findManyByTwoKeys(Class<E> persistentClass, String firstKeyFieldName, Object firstKey, String secondKeyFieldName, Object secondKey);
	
	/**
	 *  Searches for various entities by query string.
	 * 
	 * @param resultClass  Class of the entity to be searched for
	 * @param queryClass  Class of the entity to be searched for
	 * @param queryString Query string
	 * @param args Additional arguments
	 * @return  List of entities form search
	 */
	<E extends DaObject> List<E> findManyByQuery(Class<E> resultClass, Class<?> queryClass, String queryString, Object[] args);
}
