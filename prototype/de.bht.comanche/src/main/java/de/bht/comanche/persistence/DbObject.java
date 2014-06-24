package de.bht.comanche.persistence;

import java.util.List;

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
	
	public DbObject find(long i_oid) throws NoPersistentClassExc, OidNotFoundExc {
		return pool.find(this.getClass(), i_oid);
	}
	
	public List<? extends DbObject> findAll(Class<DbObject> i_persistentClass) throws NoPersistentClassExc {
		return pool.findAll(i_persistentClass);
	}
	
	public List<? extends DbObject> findManyByQuery(Class<DbObject> i_resultClass, String i_queryString, Object[] i_args) throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc, ArgumentTypeExc {
		return pool.findManyByQuery(i_resultClass, i_queryString, i_args);
	}
}
