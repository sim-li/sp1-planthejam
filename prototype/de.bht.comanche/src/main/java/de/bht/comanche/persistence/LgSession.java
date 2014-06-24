package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.logic.Pool;

public abstract class LgSession {
	private Pool pool;
	
	public LgSession() {
		this.pool = PoolImpl.getInstance();
	}
	
	public DbObject find(Class<DbObject> i_persistentClass, long i_oid) throws NoPersistentClassExc, OidNotFoundExc {
		return pool.find(i_persistentClass, i_oid);
	}
	
	public List<? extends DbObject> findAll(Class<DbObject> i_persistentClass) throws NoPersistentClassExc {
		return pool.findAll(i_persistentClass);
	}
	
	public List<? extends DbObject> findManyByQuery(Class<DbObject> i_resultClass, String i_queryString, Object[] i_args) throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc, ArgumentTypeExc {
		return pool.findManyByQuery(i_resultClass, i_queryString, i_args);
	}
}
