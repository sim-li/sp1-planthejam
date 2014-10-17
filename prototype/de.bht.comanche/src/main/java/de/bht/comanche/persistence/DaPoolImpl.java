package de.bht.comanche.persistence;

import static multex.MultexUtil.create;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import de.bht.comanche.logic.LgObject;

//TODO not ready for multex ------> Seb
public class DaPoolImpl<E> implements DaPool<E> {
	private final String persistenceUnitName = "planthejam.jpa";
	private EntityManager em;
	private EntityManagerFactory entityManagerFactory;
	
	public DaPoolImpl () {
		entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
	}
	
	@Override
	public void beginTransaction() {
		em = entityManagerFactory.createEntityManager();
		EntityTransaction tr = em.getTransaction();
		tr.begin();
	}

	@Override
	public void endTransaction(boolean success) {
		EntityTransaction tr = em.getTransaction();
		try {
			if (success) {
				tr.commit();
			} else {
				tr.rollback();
			}
		}
		catch (RollbackException e) { //TODO
			tr.rollback();
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public void save(E io_object) throws EntityExistsExc, IllegalArgumentExc, TransactionRequiredExc {
		em.persist(io_object);
	}

	@Override
	public E merge(E io_object) throws IllegalArgumentExc, TransactionRequiredExc {
		return em.merge(io_object);
	}
	
	//------------------------------------------ multex-ready ----
	@Override
	public void delete(E io_object) throws multex.Exc {
		try {
			em.remove(em.contains(io_object) ? io_object : em.merge(io_object));
		} catch (Exception ex) {
			throw create(DeleteExc.class, ex, io_object);
		}
	}

	//------------------------------------------ multex-ready ----
	@Override
	public E find(Class<E> i_persistentClass, Long i_oid) throws DaNoPersistentClassExc {
		if (!LgObject.class.isAssignableFrom(i_persistentClass)) {
			throw create(DaNoPersistentClassExc.class, "add some helpful parameters here"); // <<--- TODO
		}
		E result = em.find(i_persistentClass, i_oid);
		if (result == null) {
			throw create(DaOidNotFoundExc.class, i_oid, "message");
		}
		return result;
	}

	//------------------------------------------ multex-ready ----
	@Override
	public List<E> findAll(Class<E> i_persistentClass) throws DaNoPersistentClassExc {
		if (!LgObject.class.isAssignableFrom(i_persistentClass)) {
			throw create(DaNoPersistentClassExc.class, "add some helpful parameters here"); // <<--- TODO
		}
		return em.createQuery("SELECT e FROM " + i_persistentClass.getSimpleName() + "e", i_persistentClass).getResultList();
	}

	//------------------------------------------ multex-ready ----
	@Override
	public List<E> findManyByQuery(Class<E> i_resultClass, String i_queryString, Object[] i_args) throws DaNoPersistentClassExc, /*DaNoQueryClassExc,*/ DaArgumentCountExc /*, DaArgumentTypeExc*/ {
		if (!LgObject.class.isAssignableFrom(i_resultClass)) {
			throw create(DaNoPersistentClassExc.class, "add some helpful parameters here"); // <<--- TODO
		}
//		if (condition) {
//			throw create(DaNoQueryClassExc.class, "TODO") 
//		}
		if (wrongArgumentCount(i_queryString, i_args)) {
			throw create(DaArgumentCountExc.class, "TODO");
		}
//		if (condition) {
//			throw create(DaArgumentTypeExc.class, "TODO") 
//		}
		String qlString = String.format(i_queryString, i_args);
		return em.createQuery(qlString, i_resultClass).getResultList();
	}

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

	
	/**
	 * Occured at "{0}". User with oid: "{1}" and name: "{2}"
	 */
	@SuppressWarnings("serial")
	public static final class DataAccessExc extends multex.Exc {}
	
	/**
	 * Could not delete object "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class DeleteExc extends multex.Exc {}
	
	/**
	 * TODO add meaningful message here: "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class DaNoPersistentClassExc extends multex.Exc {}
	
	/**
	 * TODO add meaningful message here: "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class DaArgumentCountExc extends multex.Exc {}
	
	/**
	 * TODO add meaningful message here: "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class DaOidNotFoundExc extends multex.Exc {}
	
	/**
	 * TODO add meaningful message here: "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class EntityExistsExc extends multex.Exc {}
	
	/**
	 * TODO add meaningful message here: "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class TransactionRequiredExc extends multex.Exc {}
	
	/**
	 * TODO add meaningful message here: "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class IllegalArgumentExc extends multex.Exc {}
	
	/**
	 * TODO add meaningful message here: "{0}"
	 */
	@SuppressWarnings("serial")
	public static final class DaNotFoundExc extends multex.Exc {}
}
