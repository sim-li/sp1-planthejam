package de.bht.comanche.persistence;

import static multex.MultexUtil.create;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import multex.Failure;

/**
 * This class is a concrete implementation DaPool for Hibernate/JPA
 * and provides all basic data access operations.
 * 
 * @author Simon Lischka
 */

public class DaHibernateJpaPool implements DaPool {
	
	private EntityManager entityManager;
	
	public DaHibernateJpaPool() {
		try {
			final EntityManagerFactory emf = DaEmProvider.getInstance().getEntityManagerFactory();
			this.entityManager = emf.createEntityManager();
		} catch (final Exception ex) {
			throw new Failure("Could not initialize JPA Entity Manager.", ex); // TODO MultexUtil.create benutzen
		}
	}
    
	@Override
	public void insert(final DaObject io_object) {
		this.entityManager.persist(io_object);
	}

	@Override
	public boolean contains(final DaObject io_object) {
		return this.entityManager.contains(io_object);
	}
	
	@Override
	public <E extends DaObject> E save(final DaObject io_object) {
		io_object.attach(this);
		@SuppressWarnings("unchecked")
		final E result = (E) this.entityManager.merge(io_object);
		return result;
	}

	@Override
	public boolean delete(final DaObject io_object) {
		final boolean isPersistent = this.entityManager.contains(io_object);
		this.entityManager.remove(isPersistent ? io_object : this.entityManager.merge(io_object));
		return isPersistent;
	}

	@Override
	public <E extends DaObject> E find(final Class<E> persistentClass, final long oid) {
		checkPersistentClass(persistentClass);
		final E result = this.entityManager.find(persistentClass, oid);
		if (result == null) {
		}
		result.attach(this);
		return result;
	}

	@Override
	public <E extends DaObject> List<E> findAll(final Class<E> persistentClass) {
		final Query query = this.entityManager.createQuery("SELECT e FROM " + persistentClass.getSimpleName() + " e", persistentClass); 
		@SuppressWarnings("unchecked")
		final List<E> results = query.getResultList();
		for (final E item : results) {
			item.attach(this); // TODO Test!
		}
		return results;
	}

	@Override
	public <E extends DaObject> E findOneByKey(Class<E> persistentClass,
			String keyFieldName, Object keyFieldValue) throws DaFindOneByKeyExc{
		final List<E> results = findManyByKey(persistentClass, keyFieldName, keyFieldValue);
		if(results.isEmpty()){
			throw create(DaFindOneByKeyExc.class, persistentClass.getClass(), keyFieldName, keyFieldValue);
		}
		final E result = results.get(0);
		result.attach(this);
		return result;
	}
	
	/**
	 * Could not find entry for class "{0}" with field "{1}" and value "{2}".
	 */
	@SuppressWarnings("serial")
	public static final class DaFindOneByKeyExc extends multex.Exc {}
	
	public <E extends DaObject> E findOneByTwoKeys(final Class<E> persistentClass, final String firstKeyFieldName, final Object firstKey, final String secondKeyFieldName, final Object secondKey) {
		final List<E> results = findManyByTwoKeys(persistentClass, firstKeyFieldName, firstKey, secondKeyFieldName, secondKey);
		final E result = results.get(0);
		result.attach(this);
		return result;
	}

	@Override
	public <E extends DaObject> List<E> findManyByKey(final Class<E> persistentClass, final String keyFieldName, final Object keyFieldValue) {
		checkPersistentClass(persistentClass);
		final String className = persistentClass.getName();
		final Query query = this.entityManager.createQuery("select o from " + className + " o where " + keyFieldName + " = :keyValue ORDER BY o.oid DESC");
		query.setParameter("keyValue", keyFieldValue);
		@SuppressWarnings("unchecked")
		final List<E> results = query.getResultList();
		for (final E item : results) {
			item.attach(this); // TODO Test!
		}
		return results;
	}
	
	public <E extends DaObject> List<E> findManyByTwoKeys(final Class<E> persistentClass, final String firstKeyFieldName, final Object firstKey, final String secondKeyFieldName, final Object secondKey) {
		checkPersistentClass(persistentClass);
		final String className = persistentClass.getName();
		final Query query = this.entityManager.createQuery("select o from " + className + " o where " + firstKeyFieldName + " = :firstKeyValue AND "
				+ secondKeyFieldName + " = :secondKeyValue");
		query.setParameter("firstKeyValue", firstKey);
		query.setParameter("secondKeyValue", secondKey);
		@SuppressWarnings("unchecked")
		final List<E> results = query.getResultList();
		for (final E item : results) {
			item.attach(this); // TODO Test!
		}
		return results;
	}

	@Override
	public <E extends DaObject> List<E> findManyByQuery(final Class<E> resultClass, final Class<?> queryClass, 
			final String queryString, final Object[] args) {
		checkPersistentClass(resultClass);
		final String qlString = String.format(queryString, args);
		final Query query = this.entityManager.createQuery(qlString, resultClass);
		@SuppressWarnings("unchecked")
		final List<E> results = query.getResultList();
		for (final E item : results) {
			item.attach(this); // TODO Test!
		}
		return results; 
	}

	public <E extends DaObject> void checkPersistentClass(final Class<E> persistentClass) {
		if (!DaObject.class.isAssignableFrom(persistentClass)) {
		}
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}
}
