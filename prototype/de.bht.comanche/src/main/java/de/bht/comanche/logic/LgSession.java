//package de.bht.comanche.logic;
//
//import java.util.List;
//
//import de.bht.comanche.persistence.ArgumentCountExc;
//import de.bht.comanche.persistence.ArgumentTypeExc;
//import de.bht.comanche.persistence.NoPersistentClassExc;
//import de.bht.comanche.persistence.NoQueryClassExc;
//import de.bht.comanche.persistence.OidNotFoundExc;
//import de.bht.comanche.persistence.Pool;
//import de.bht.comanche.persistence.PoolImpl;
//
//public class LgSession {
//	private Pool pool;
//	
//	public LgSession() {
//		this.pool = PoolImpl.getInstance();
//	}
//	
//	public DbObject find(Class<? extends DbObject> i_persistentClass, long i_oid) throws NoPersistentClassExc, OidNotFoundExc {
//		return pool.find(i_persistentClass, i_oid);
//	}
//	
//	/*
//	 * TODO: Solve problem with generics:
//	 * Remove Cast, change return type in pool.
//	 */
//	public <T extends DbObject> List<T> findAll (Class<T> i_persistentClass) throws NoPersistentClassExc {
//		return (List<T>) pool.findAll(i_persistentClass);
//	}
//	
//	public List<? extends DbObject> findManyByQuery(Class<DbObject> i_resultClass, String i_queryString, Object[] i_args) throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc, ArgumentTypeExc {
//		return pool.findManyByQuery(i_resultClass, i_queryString, i_args);
//	}
//}
