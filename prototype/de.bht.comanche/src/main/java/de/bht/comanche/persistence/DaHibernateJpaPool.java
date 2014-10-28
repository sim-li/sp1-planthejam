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
    //Runtime exc -> Failure
	//checked exc -> multex exc - 
	public DaHibernateJpaPool() {
		try {
			this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
			this.entityManager = entityManagerFactory.createEntityManager();
		} catch (Exception ex) {//create benutzen
			throw new Failure("Could not initialize JPA Entity Manager.", ex);
		}
	}

	@Override
	public void insert(DaObject io_object) {
		io_object.setPool(this);
		this.entityManager.persist(io_object);
	}

	@Override
	public void reattach(DaObject io_object) {
		this.entityManager.merge(io_object);
	}

	@Override
	public boolean save(DaObject io_object) {
		EntityManager session = getEntityManager();
		final boolean isPersistent = session.contains(io_object);
		if (!isPersistent) {
			io_object.setPool(this);
		}
		session.merge(io_object);
		return !isPersistent;
	}

	@Override
	public boolean delete(DaObject io_object) {
		EntityManager session = getEntityManager();
		final boolean isPersistent = session.contains(io_object);
		session.remove(io_object);
		io_object.setOid(DaPool.deletedOid);
		return isPersistent;
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
