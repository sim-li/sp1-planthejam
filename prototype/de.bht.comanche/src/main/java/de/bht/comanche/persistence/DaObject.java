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

/**
 * @author Simon Lischka
 */
@MappedSuperclass
public abstract class DaObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(unique = true, nullable = false)
	protected long oid = DaPool.CREATED_OID; 

	protected transient DaPool pool;

	public DaObject() {}

	protected DaObject(final DaPool pool) {
		this.pool = pool;
	}

	public DaObject attach(final DaPool pool) throws multex.Exc {
		if (this.pool != null && pool != this.pool) {
			throw create(OwningPoolChangedExc.class, this.getClass().getName(), this.getOid());
		}
		this.pool = pool;
		return this;
	}

	/**
	 *  Owning pool changed. Object of class "{0}" and with OID "{1}" 
	 */
	@SuppressWarnings("serial")
	public static final class OwningPoolChangedExc extends multex.Exc {}

	public <E extends DaObject> E attach(final E item) {
		@SuppressWarnings("unchecked")
		final E result = (E) item.attach(getPool());
		return result;
	}

	public void delete() {
		this.pool.delete(this);
	}

	public <E extends DaObject> E save() {
		return this.pool.save(this);
	}

	public <E extends DaObject> E search(final List <E> list, final long oid) {
		for (final DaObject item : list) {
			if (oid == item.getOid()) {
				@SuppressWarnings("unchecked")
				final E result = (E) item.attach(getPool());
				return result;
			}
		}
		return null;
	}

	public <E extends DaObject> E search(final E obj, final long oid) {
		if (oid == obj.getOid()) {
			@SuppressWarnings("unchecked")
			final E result = (E) obj.attach(getPool());
			return result;
		}
		return null;
	}

	public <E extends DaObject> List<E> search(final Class<E> persistentClass, final String firstKeyFieldName,
			final Object firstKey, final String secondKeyFieldName, final Object secondKey) {
		List<E> result = new ArrayList<E>();
		try{
			result = this.pool.findManyByTwoKeys(persistentClass, firstKeyFieldName, firstKey, secondKeyFieldName, secondKey);
		} catch (final Exception ex) {
			multex.Msg.printReport(System.err, ex);
			// TODO throw Multex exc when not found
		}
		return result;
	}

	/**
	 * --------------------------------------------------------------------------------------------
	 * # get(), set() methods for data access
	 * # hashCode(), toString()
	 * --------------------------------------------------------------------------------------------
	 */
	protected DaPool getPool() {
		return this.pool;
	}

	public long getOid() {
		return this.oid;
	}

	protected void setOid(final long oid) {
		this.oid = oid;
	}

	public boolean isPersistent() {
		return this.oid != DaPool.CREATED_OID && this.oid > 0;
	}

	public boolean isDeleted() {
		return this.oid == DaPool.DELETED_OID;
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
