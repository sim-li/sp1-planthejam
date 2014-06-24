package de.bht.comanche.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class PoolImpl implements Pool {
	private static PoolImpl POOL = new PoolImpl();
	private EntityManagerFactory entityManagerFactory;
	
	private PoolImpl() {
		entityManagerFactory = Persistence.createEntityManagerFactory("planthejam.jpa");
	}
	
	public static PoolImpl getInstance() {
		return POOL;
	}
	
	@Override
	public boolean save(DbObject io_object) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(io_object);
			entityManager.getTransaction().commit();
		}
		catch (PersistenceException e) {
			entityManager.getTransaction().rollback();
			return false;
		}
		finally {
			entityManager.close();
		}
		return true;
	}

	@Override
	public boolean delete(DbObject io_object) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(io_object);
			entityManager.getTransaction().commit();
		}
		catch (PersistenceException e) {
			entityManager.getTransaction().rollback();
			return false;
		}
		finally {
			entityManager.close();
		}
		return true;
	}

	@Override
	public DbObject find(Class<? extends DbObject> i_persistentClass, Long i_oid)
		throws NoPersistentClassExc, OidNotFoundExc {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		DbObject result = entityManager.find(i_persistentClass, i_oid);
		entityManager.close();
		return result;
	}

	@Override
	public List<? extends DbObject> findAll(Class<? extends DbObject> i_persistentClass)
			throws NoPersistentClassExc {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		final String qlString = "SELECT e FROM " + i_persistentClass.getSimpleName() + "e";
		List<? extends DbObject> results = entityManager.createQuery(qlString, i_persistentClass).getResultList();
		entityManager.close();
		return results;
	}

	@Override
	public List<? extends DbObject> findManyByQuery(Class<? extends DbObject> i_resultClass,
			String i_queryString, Object[] i_args)
			throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc,
			ArgumentTypeExc {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String qlString = String.format(i_queryString, i_args);
		List<? extends DbObject> results = entityManager.createQuery(qlString, i_resultClass).getResultList();
		entityManager.close();
		return results;
	}
}
