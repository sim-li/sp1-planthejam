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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DbObject find(Class<?> i_persistentClass, Integer i_oid)
			throws NoPersistentClassExc, OidNotFoundExc {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DbObject> findAll(Class<?> i_persistentClass)
			throws NoPersistentClassExc {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DbObject> findManyByQuery(Class<?> i_resultClass,
			Class<?> i_queryClass, Object[] i_args)
			throws NoPersistentClassExc, NoQueryClassExc, ArgumentCountExc,
			ArgumentTypeExc {
		// TODO Auto-generated method stub
		return null;
	}

}
