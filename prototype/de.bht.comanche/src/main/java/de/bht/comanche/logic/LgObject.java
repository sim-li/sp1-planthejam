package de.bht.comanche.logic;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Duc Tung Tong, Beuth Hochschule für Technik Berlin, SWP1 
 * Dies ist eine abstract Klasse, die von Serializable erbt
 * gibt automatisch das oid für jedes Objekt, welches in der Datenbank hinzugefügt wird
 * und durch getOid() dieses oid zurückgeben. 
 */
@MappedSuperclass
public abstract class LgObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long oid; 

	public long getOid() {
		return oid;
	}
	
	//changed to public for REST-Service
	public void setOid(long oid) {
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


