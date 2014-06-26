package de.bht.comanche.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public abstract class DbObject {
	
	private final transient Pool pool;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	public DbObject() {
		this.pool = PoolImpl.getInstance();
	}
	
	public Long getId() {
    	return id;
    }
    
    public boolean save() {
    	// Save wird in REST Schnittstelle von neuen Begin/EndTransaction 
    	// aus Pool gekapselt
    	return pool.save(this);
    }
    
	public boolean delete() {
		return pool.delete(this);
	}
}