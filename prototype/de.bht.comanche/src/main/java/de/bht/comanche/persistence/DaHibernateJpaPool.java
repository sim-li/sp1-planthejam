package de.bht.comanche.persistence;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import multex.Failure;

public class DaHibernateJpaPool implements DaPool {
	private final String persistenceUnitName = "planthejam.jpa";
	private EntityManager entityManager;
	private EntityManagerFactory entityManagerFactory;

	public DaHibernateJpaPool () {
		try {
			this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
			this.entityManager = entityManagerFactory.createEntityManager();
		} catch (Exception ex) {
			throw new Failure("Could not initialize JPA Entity Manager.", ex);
		}
	}

	@Override
	public void insert(DaObject io_object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reattach(DaObject io_object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean save(DaObject io_object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(DaObject io_object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <E extends DaObject> E find(Class<E> persistentClass, Long oid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends DaObject> List<E> findAll(Class<E> persistentClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends DaObject> E findOneByKey(Class<E> persistentClass,
			String keyFieldName, Object keyFieldValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends DaObject> List<E> findManyByKey(Class<E> persistentClass,
			String keyFieldName, Object keyFieldValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends DaObject> List<E> findManyByQuery(Class<E> resultClass,
			Class queryClass, Object[] args) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public EntityManager getEntityManager() {
    	return this.entityManager;
    }

}
