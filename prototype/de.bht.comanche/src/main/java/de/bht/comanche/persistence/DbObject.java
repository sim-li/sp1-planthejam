package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class DbObject {
	
	private Pool pool;
	private Long id;
	
	public DbObject() {
		this.pool = PoolImpl.getInstance();
	}
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
    	return id;
    }

    private void setId(Long id) {
    	this.id = id;
    }
    
    public boolean save() {
    	return pool.save(this);
    }
    
	public boolean delete(DbObject io_object) {
		return pool.delete(this);
	}
	
	public DbObject find(Class<DbObject> i_persistentClass, Integer i_oid) throws NoPersistentClassExc, OidNotFoundExc {
		return pool.find(i_persistentClass, i_oid);
	}
	
	public List<DbObject> findAll(Class<DbObject> i_persistentClass) throws NoPersistentClassExc {
		return pool.findAll(i_persistentClass);
	}
	
	public List<DbObject> findManyByQuery(Class<DbObject> i_resultClass, String i_queryString, Object[] i_args) throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc, ArgumentTypeExc {
		return pool.findManyByQuery(i_resultClass, i_queryString, i_args);
	}
}
