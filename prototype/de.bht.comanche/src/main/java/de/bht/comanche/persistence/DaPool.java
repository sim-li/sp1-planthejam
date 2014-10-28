package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import de.bht.comanche.persistence.DaHibernateJpaPool.DaArgumentCountExc;
import de.bht.comanche.persistence.DaHibernateJpaPool.DaNoPersistentClassExc;
import de.bht.comanche.persistence.DaHibernateJpaPool.DaOidNotFoundExc;

public interface DaPool {
	
	static final long createdOid = 0;
	static final long deletedOid = -1; 
	
	public void save(E io_object);
	public E merge (E io_object);
	public void delete(E io_object);
	public E find(Class<E> i_persistentClass, Long i_oid) throws DaNoPersistentClassExc, DaOidNotFoundExc;
	public List<E> findAll(Class<E> i_persistentClass) throws DaNoPersistentClassExc;
	public List<E> findManyByQuery(Class<E> i_resultClass, String i_queryString, Object[] i_args) throws DaNoPersistentClassExc, DaArgumentCountExc;
	public void flush();
	public String getPersistenceUnitName();
    public EntityManager getEntityManager();	
}
