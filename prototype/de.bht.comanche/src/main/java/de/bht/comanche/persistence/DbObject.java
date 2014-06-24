package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.logic.Pool;

public abstract class DbObject {
	
	private Pool pool;
	private long id;
	
	public DbObject() {
		this.pool = PoolImpl.getInstance();
	}
	
	public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public boolean save() {
    	return pool.save(this);
    }
    
	public boolean delete() {
		return pool.delete(this);
	}
}