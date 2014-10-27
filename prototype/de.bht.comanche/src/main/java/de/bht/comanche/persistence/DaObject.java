package de.bht.comanche.persistence;

import java.io.Serializable;
import static multex.MultexUtil.create;
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
	protected long oid = DaPool.createdOid; 
	
	private transient DaPool pool;
	
	public long getOid() {
		return oid;
	}
    
	public boolean isPersistent() {
		return oid != DaPool.createdOid && oid > 0;
	}
	
	public boolean isDeleted() {
		return oid == DaPool.deletedOid;
	}
	
	/**
     *  Owning pool changed. Object of class "{0}" and with OID "{1}" 
     */
    @SuppressWarnings("serial")
    public static final class OwningPoolChangedExc extends multex.Exc {}

    protected void setPool(DaPool pool) throws multex.Exc {
    	if (this.pool != null && pool != this.pool) {
    		throw create(OwningPoolChangedExc.class, this.getClass().getName(), this.getOid());
    	}
    	this.pool = pool;
	}
    
    public DaObject() {}
    
    protected DaObject(DaPool pool) {
    	this.pool = pool;
    }
     
    protected DaPool getPool() {
    	return pool;
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


