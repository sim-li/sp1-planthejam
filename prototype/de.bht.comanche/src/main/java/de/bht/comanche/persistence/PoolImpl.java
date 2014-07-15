package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.server.exceptions.persistence.ArgumentCountException;
import de.bht.comanche.server.exceptions.persistence.ArgumentTypeException;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.NoQueryClassException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

public class PoolImpl<E> implements Pool<E> {
	private final String persistenceUnitName = "planthejam.jpa";
	private EntityManager em;
	private EntityManagerFactory entityManagerFactory;
	
	public PoolImpl () {
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
		catch (RollbackException e) {
			tr.rollback(); // TODO is das sinnvoll so?
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public void save(E io_object) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException {
		em.persist(em.contains(io_object) ? io_object : em.merge(io_object));
	}

	@Override
	public void delete(E io_object) throws IllegalArgumentException, TransactionRequiredException {
		em.remove(em.contains(io_object) ? io_object : em.merge(io_object));
	}

	@Override
	public E find(Class<E> i_persistentClass, Long i_oid) throws NoPersistentClassException, OidNotFoundException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		E result = entityManager.find(i_persistentClass, i_oid);
		entityManager.close();
		return result;
	}

	@Override
	public List<E> findAll(Class<E> i_persistentClass) throws
			NoPersistentClassException {
		final String qlString = "SELECT e FROM " + i_persistentClass.getSimpleName() + "e";
		List<E> results = em.createQuery(qlString, i_persistentClass).getResultList();
		return results;
	}

	@Override
	public List<E> findManyByQuery(Class<E> i_resultClass,
			String i_queryString, Object[] i_args)
			throws NoPersistentClassException, NoQueryClassException, ArgumentCountException,
			ArgumentTypeException {
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
