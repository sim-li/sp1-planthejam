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
    
    public void save() throws EntityExistsException, TransactionRequiredException, IllegalArgumentException {
    	// Save wird in REST Schnittstelle von neuen Begin/EndTransaction 
    	// aus Pool gekapselt
		pool.save(this);
    }
    
	public void delete() throws TransactionRequiredException, IllegalArgumentException {
		pool.delete(this);
	}
}