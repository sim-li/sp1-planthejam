package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.DbObject;

public class PoolImpl implements Pool {
	private static PoolImpl POOL = new PoolImpl();
	private EntityManager entityManager;
	private EntityManagerFactory entityManagerFactory;
	
	private PoolImpl() {
		entityManagerFactory = Persistence.createEntityManagerFactory("planthejam.jpa");
	}
	
	public static PoolImpl getInstance() { 
		return POOL;
	}
	
	@Override
	public void beginTransaction() {
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
	public void save(DbObject io_object) throws EntityExistsException, IllegalArgumentException, TransactionRequiredException {
		entityManager.persist(io_object);
	}

	@Override
	public void delete(DbObject io_object) throws IllegalArgumentException, TransactionRequiredException {
		entityManager.remove(io_object);
	}

	@Override
	public DbObject find(Class<? extends DbObject> i_persistentClass, Long i_oid) throws NoPersistentClassExc, OidNotFoundExc {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		DbObject result = entityManager.find(i_persistentClass, i_oid);
		entityManager.close();
		return result;
	}

	@Override
	public List<? extends DbObject> findAll(Class<? extends DbObject> i_persistentClass) throws
			NoPersistentClassExc {
		final String qlString = "SELECT e FROM " + i_persistentClass.getSimpleName() + "e";
		List<? extends DbObject> results = entityManager.createQuery(qlString, i_persistentClass).getResultList();
		return results;
	}

	@Override
	public List<? extends DbObject> findManyByQuery(Class<? extends DbObject> i_resultClass,
			String i_queryString, Object[] i_args)
			throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc,
			ArgumentTypeExc {
		String qlString = String.format(i_queryString, i_args);
		List<? extends DbObject> results = entityManager.createQuery(qlString, i_resultClass).getResultList();
		return results;

	}
}
