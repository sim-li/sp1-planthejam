package de.bht.comanche.persistence;

import static multex.MultexUtil.create;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import de.bht.comanche.logic.LgGroup;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgMember;
import de.bht.comanche.logic.LgUser;

/**
 * @author Simon Lischka
 */
@MappedSuperclass
public abstract class DaObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(unique = true, nullable = false)
	protected long oid = DaPool.createdOid; 

	protected transient DaPool pool;

	public DaObject() {}

	protected DaObject(DaPool pool) {
		this.pool = pool;
	}

	/**
	 *  Owning pool changed. Object of class "{0}" and with OID "{1}" 
	 */
	@SuppressWarnings("serial")
	public static final class OwningPoolChangedExc extends multex.Exc {}

	public DaObject attach(DaPool pool) throws multex.Exc {
		if (this.pool != null && pool != this.pool) {
			throw create(OwningPoolChangedExc.class, this.getClass().getName(), this.getOid());
		}
		this.pool = pool;
		return this;
	}
	
	public <E extends DaObject> E attach(E item) {
		@SuppressWarnings("unchecked")
		final E result = (E) item.attach(getPool());
		return result;
	}
	
	public void delete() {
		pool.delete(this);
	}
	
	public <E extends DaObject> E save() {
		return pool.save(this);
	}
	
	public <E extends DaObject> E search(List <E> list, long oid) {
		for (DaObject item : list) {
			if (oid == item.getOid()) {
				@SuppressWarnings("unchecked")
				final E result = (E) item.attach(getPool());
				return result;
			}
		}
		return null;
	}
	
	public <E extends DaObject> E search( E obj, long oid) {
			if (oid == obj.getOid()) {
				@SuppressWarnings("unchecked")
				final E result = (E) obj.attach(getPool());
				return result;
		}
		return null;
	}
	
	public <E extends DaObject> List<E> search(Class<E> persistentClass, String firstKeyFieldName,
			Object firstKey, String secondKeyFieldName, Object secondKey) {
		// throw exc when not found
		List<E> result = null;
		try{
			result = pool.findManyByTwoKeys(persistentClass, firstKeyFieldName, firstKey, secondKeyFieldName, secondKey);
		} catch (Exception e) {
			multex.Msg.printReport(System.err, e);// TODO Multex exc
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
		return pool;
	}

	public long getOid() {
		return oid;
	}

	protected void setOid(long oid) {
		this.oid = oid;
	}

	public boolean isPersistent() {
		return oid != DaPool.createdOid && oid > 0;
	}

	public boolean isDeleted() {
		return oid == DaPool.deletedOid;
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


