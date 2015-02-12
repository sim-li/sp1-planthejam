package de.bht.comanche.persistence;

import static multex.MultexUtil.create;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.bht.comanche.rest.ReInviteService.RestGetInviteFailure;

/**
 * Abstract base class for all entities that will be persisted.
 * Fulfills all basic requirements to interact with the persistence unit.
 * This includes an object id field and a local reference to the pool.
 * 
 * <code>DaObject</code> provides basic operations like <code>save</code>
 * or <code>delete</code> which should be overwritten when a different
 * functionality is needed. The introduction of new method signatures for 
 * these operations should be avoided when possible.
 * 
 * @author Simon Lischka
 * @author Max Novichkov
 */
@MappedSuperclass
public abstract class DaObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * Default object id of a newly created entity. Will be overwritten when
     * persisted. 
     */
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(unique = true, nullable = false)
	protected long oid = DaPool.CREATED_OID; 
   
	/**
	 * Local reference to the <code>pool</code> used for persistence operations.
	 */
	protected transient DaPool pool;

	/**
	 * Links a pool to this entity. 
	 * 
	 * Will throw exception when a different pool is already set.
	 * 
	 * @param pool <code>Pool</code> to be attached to this entity
	 * @return This object with pool reference
	 */
	public DaObject attach(final DaPool pool) throws multex.Exc {
		if (this.pool != null && pool != this.pool) {
			throw create(OwningPoolChangedExc.class, this.getClass().getName(), this.getOid());
		}
		this.pool = pool;
		return this;
	}
	
	public <E extends DaObject> E saveUnattached(final E other) {
		return attachPoolFor(other).save();
	}

	
	@SuppressWarnings("serial")
	public static final class OwningPoolChangedExc extends multex.Exc {}

	/**
	 * Links the pool of this entity to another entity.
	 * 
	 * @param other Receiving entity
	 * @return Receiving entity with the attached pool
	 */
	/**
	 * Attaching pool of "{0}" to object "{1}" failed. Pool is NULL.
	 */
	@SuppressWarnings("serial")
	public static final class DaObjectUndefinedPoolWhileAttachingFailure extends multex.Failure {}
	public <E extends DaObject> E attachPoolFor(final E other) {

		if (this.pool == null) {
			throw create(DaObjectUndefinedPoolWhileAttachingFailure.class, this, other);
		}
		// @TODO Throw no pool exception when other object doesn't contain pool
		@SuppressWarnings("unchecked")
		final E result = (E) other.attach(this.pool);
		return result;
	}

	/**
	 * Deletes this entity from its pool
	 */
	public void delete() {
		this.pool.delete(this);
	}

	/**
	 * Saves this entity to the DB. If the entity already
	 * exists with the same id, modified fields are updated
	 * with the new entity.
	 * 
	 * After saving, the object is tracked by the entity manager.
	 * Updates to the object will be automatically executed without
	 * the need for an additional save.
	 * 
	 * @return Saved entity
	 */
	// @TODO Testing: Check if all or only updated values are overwritten
	/**
	 * Pool is NULL for object "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class DaObjectUndefinedPoolFailure extends multex.Failure {}
	public <E extends DaObject> E save() {
		if (this.pool == null) {
			throw create(DaObjectUndefinedPoolFailure.class, this);
		}
		return this.pool.save(this);
	}

	/**
	 * Searches a list of <code>DaObject</code>s by object id. 
	 * 
	 * Method provides a default way of retrieving internal collections.
	 * 
	 * @param list List of <code>DaObject</code>s to be searched
	 * @param oid Object id value of element to search
	 * @return Matching element or null when not found
	 */
	public <E extends DaObject> E search(final List <E> list, final long oid) {
		// @TODO Check if exception when not found necessary.
		for (final DaObject item : list) {
			if (oid == item.getOid()) {
				@SuppressWarnings("unchecked")
				final E result = (E) item.attach(getPool());
				return result;
			}
		}
		return null;
	}

	/**
	 * @deprecated In the current implementation this is not the default way of
	 * searching for objects. In future releases, this may be implemented as the 
	 * standard way of accessing collections though (requires redesign and integration
	 * of direct DB operations).
	 * 
	 * Searches an object by primary and foreign key/value pairs. 
	 * 
	 * @param persistentClass Class of resulting object
	 * @param firstKeyFieldName Primary key name
	 * @param firstKey Primary key value
	 * @param secondKeyFieldName Secondary key name
	 * @param secondKey Secondary key value
	 * @return Matching entity
	 */
	public <E extends DaObject> List<E> search(final Class<E> persistentClass, final String firstKeyFieldName,
			final Object firstKey, final String secondKeyFieldName, final Object secondKey) {
		List<E> result = new ArrayList<E>();
		try{
			result = this.pool.findManyByTwoKeys(persistentClass, firstKeyFieldName, firstKey, secondKeyFieldName, secondKey);
		} catch (final Exception ex) {
			multex.Msg.printReport(System.err, ex);
		}
		return result;
	}

	protected <E extends DaObject> E findOneByKey(Class<E> persistentClass, String keyFieldName, Object keyFieldValue) {
		return pool.findOneByKey(persistentClass, keyFieldName, keyFieldValue);
	}
	
	protected <E extends DaObject> List<E> findManyByKey(Class<E> persistentClass, String keyFieldName, Object keyFieldValue){
		return pool.findManyByKey(persistentClass, keyFieldName, keyFieldValue);
	};
	
	public <E extends DaObject> List<E> findManyByQuery(final Class<E> resultClass, final Class<?> queryClass, 
			final String queryString, final Object[] args) {
		return pool.findManyByQuery(resultClass, queryClass, queryString, args);
	}
	
	@JsonIgnore
	public boolean isPersistent() {
		return this.oid != DaPool.CREATED_OID && this.oid > 0;
	}
	@JsonIgnore
	public boolean isDeleted() {
		return this.oid == DaPool.DELETED_OID;
	}
	
	protected DaPool getPool() {
		return this.pool;
	}

	public long getOid() {
		return this.oid;
	}

	public void setOid(final long oid) {
		this.oid = oid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (oid ^ (oid >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DaObject other = (DaObject) obj;
		if (oid != other.oid)
			return false;
		return true;
	}
}
