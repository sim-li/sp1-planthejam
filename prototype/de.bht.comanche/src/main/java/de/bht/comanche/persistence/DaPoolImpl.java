package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.exceptions.DaArgumentCountException;
import de.bht.comanche.exceptions.DaArgumentTypeException;
import de.bht.comanche.exceptions.DaNoPersistentClassException;
import de.bht.comanche.exceptions.DaNoQueryClassException;
import de.bht.comanche.exceptions.DaOidNotFoundException;

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
	public void save(E io_object) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException {
		em.persist(io_object);
	}

	@Override
	public E merge(E io_object) throws IllegalArgumentException, TransactionRequiredException {
		return em.merge(io_object);
	}
	
	@Override
	public void delete(E io_object) throws IllegalArgumentException, TransactionRequiredException {
		em.remove(em.contains(io_object) ? io_object : em.merge(io_object));
	}

	@Override
	public E find(Class<E> i_persistentClass, Long i_oid) throws DaNoPersistentClassException, DaOidNotFoundException {
		E result = em.find(i_persistentClass, i_oid);
		return result;
	}

	@Override
	public List<E> findAll(Class<E> i_persistentClass) throws
			DaNoPersistentClassException {
		final String qlString = "SELECT e FROM " + i_persistentClass.getSimpleName() + "e";
		List<E> results = em.createQuery(qlString, i_persistentClass).getResultList();
		return results;
	}

	@Override
	public List<E> findManyByQuery(Class<E> i_resultClass,
			String i_queryString, Object[] i_args)
			throws DaNoPersistentClassException, DaNoQueryClassException, DaArgumentCountException,
			DaArgumentTypeException {
		String qlString = String.format(i_queryString, i_args);
		List<E> results = em.createQuery(qlString, i_resultClass).getResultList();
		return results;

	}
	
	@Override
	public void flush() {
		em.flush();
	}
	
	@Override
	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

}
