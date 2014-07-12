package de.bht.comanche.logic;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DbObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long oid; // FIXME ("public" for testing) Alert: Change to private immediately

	public long getOid() {
		return oid;
	}
	
    /**
     * Attention!	
     * @param dbObject
     */
	public void setIdFrom(DbObject dbObject) {
		this.oid = dbObject.getOid();
	}
	
	/**
	 * Attention!
	 * @param oid
	 */
	public void setOid(long oid) {
		this.oid = oid;
	}
}


