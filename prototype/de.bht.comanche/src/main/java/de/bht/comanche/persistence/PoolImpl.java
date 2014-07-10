package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.transaction.TransactionRequiredException;

public class PoolImpl<E> implements Pool<E> {
	private EntityManager entityManager;
	private EntityManagerFactory entityManagerFactory;
	
	@Override
	public void beginTransaction() {
		entityManagerFactory = Persistence.createEntityManagerFactory("planthejam.jpa");
		entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction tr = entityManager.getTransaction();
		tr.begin();
	}

	@Override
	public void endTransaction(boolean success) {
		EntityTransaction tr = entityManager.getTransaction();
		try {
			if (success) {
				tr.commit();
			} else {
				tr.rollback();
			}
		}
		catch (RollbackException e) {
			tr.rollback();
		}
		finally {
			entityManager.close();
		}
	}
	
	@Override
	public void save(E io_object) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException {
		entityManager.persist(io_object);
	}

	@Override
	public void delete(E io_object) throws IllegalArgumentException, TransactionRequiredException {
		entityManager.remove(io_object);
	}

	@Override
	public E find(Class<E> i_persistentClass, Long i_oid) throws NoPersistentClassExc, OidNotFoundExc {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		E result = entityManager.find(i_persistentClass, i_oid);
		entityManager.close();
		return result;
	}

	@Override
	public List<E> findAll(Class<E> i_persistentClass) throws
			NoPersistentClassExc {
		final String qlString = "SELECT e FROM " + i_persistentClass.getSimpleName() + "e";
		List<E> results = entityManager.createQuery(qlString, i_persistentClass).getResultList();
		return results;
	}

	@Override
	public List<E> findManyByQuery(Class<E> i_resultClass,
			String i_queryString, Object[] i_args)
			throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc,
			ArgumentTypeExc {
		String qlString = String.format(i_queryString, i_args);
		List<E> results = entityManager.createQuery(qlString, i_resultClass).getResultList();
		return results;

	}
}
