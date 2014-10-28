package de.bht.comanche.persistence;

import static multex.MultexUtil.create;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DaHibernateJpaPool implements DaPool {
	private final String persistenceUnitName = "planthejam.jpa";
	private EntityManager em;
	private EntityManagerFactory entityManagerFactory;

	public DaHibernateJpaPool () {
		entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
	}

    public EntityManager getEntityManager() {
    	return em;
    }
    
	@Override
	public void save(DaObject io_object) {
		em.persist(io_object);
	}

	@Override
	public E merge(E io_object) {
		return em.merge(io_object);
	}

	@Override
	public void delete(E io_object) {
		em.remove(em.contains(io_object) ? io_object : em.merge(io_object));
	}

	@Override
	public <T extends DaObject> T find(Class<T> i_persistentClass, Long i_oid) throws DaNoPersistentClassExc, DaOidNotFoundExc {
		if (!DaObject.class.isAssignableFrom(i_persistentClass)) {
			throw create(DaNoPersistentClassExc.class, i_persistentClass); 
		}
		E result = em.find(i_persistentClass, i_oid);
//		if (result == null) {
//			throw create(DaOidNotFoundExc.class, i_oid);
//		}
		return result;
	}

	/**
	 * No entry found for oid "{0}" in the database
	 */
	@SuppressWarnings("serial")
	public static final class DaOidNotFoundExc extends multex.Exc {}

	@Override
	public List<E> findAll(Class<E> i_persistentClass) throws DaNoPersistentClassExc {
		if (!DaObject.class.isAssignableFrom(i_persistentClass)) {
			throw create(DaNoPersistentClassExc.class, i_persistentClass);
		}
		return em.createQuery("SELECT e FROM " + i_persistentClass.getSimpleName() + "e", i_persistentClass).getResultList();
	}

	@Override
	public List<E> findManyByQuery(Class<E> i_resultClass, String i_queryString, Object[] i_args) throws DaNoPersistentClassExc, DaArgumentCountExc {
		if (!DaObject.class.isAssignableFrom(i_resultClass)) {
			throw create(DaNoPersistentClassExc.class, i_resultClass); 
		}
		if (wrongArgumentCount(i_queryString, i_args)) {
			throw create(DaArgumentCountExc.class, i_queryString, i_args.length);
		}
		String qlString = String.format(i_queryString, i_args);
		return em.createQuery(qlString, i_resultClass).getResultList();
	}

	/**
	 * The class "{0}" is no persistent class
	 */
	@SuppressWarnings("serial")
	public static final class DaNoPersistentClassExc extends multex.Exc {}

	/**
	 * Query string "{0}" does not match number of arguments "{1}"
	 */
	@SuppressWarnings("serial")
	public static final class DaArgumentCountExc extends multex.Exc {}

	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	private boolean wrongArgumentCount(String i_queryString, Object[] i_args) {
		return countOccurences(i_queryString, "%") != i_args.length;
	}

	private int countOccurences(String i_queryString, String pattern) {
		return i_queryString.length() - i_queryString.replace(pattern, "").length();
	}
	
	@Override
	public List findByField(String fieldName, Object fieldValue) throws DaNoPersistentClassExc { 
		final String OBJECT_NAME = type.getSimpleName();
		String [] args = {
				fieldName,
				OBJECT_NAME,
				(String) fieldValue
		};
		return pool.findManyByQuery(type, "SELECT c FROM %2$s AS c WHERE c.%1$s LIKE '%3$s'", args);
	}
}
