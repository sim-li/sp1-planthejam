package de.bht.comanche.logic;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Duc Tung Tong
 */
@MappedSuperclass
public abstract class LgObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long oid; 

	/**
	 *  Default constructor. The default OID value indicates
	 *  an object that wasn't persisted jet.
	 */
	public LgObject() {
		this.oid = -1;
	}

	/**
	 * Constructor for manually initializing an object
	 * with a custom id.
	 * 
	 * @param oid Custom id
	 */
	public LgObject(long oid) {
		this.oid = oid;
	}
	
	public long getOid() {
		return oid;
	}
	
	private void setOid(long oid) {
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
		LgObject other = (LgObject) obj;
		if (oid != other.oid)
			return false;
		return true;
	}
}


