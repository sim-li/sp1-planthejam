package de.bht.comanche.persistence;

import javax.persistence.EntityExistsException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.transaction.TransactionRequiredException;

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
    	try {
			pool.save(this);
			return true;
		} catch (EntityExistsException | TransactionRequiredException
				| IllegalArgumentException e) {
			return false;
		}
    }
    
	public boolean delete() {
		try {
			pool.delete(this);
			return true;
		} catch (TransactionRequiredException | IllegalArgumentException e) {
			return false;
		}
	}
}