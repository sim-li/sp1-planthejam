package de.bht.comanche.persistence;
import static multex.MultexUtil.create;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import multex.Failure;

/**
 * 
 * @author Simon Lischka
 * TODO: Handle Exceptions
 */

// Contract: Every newly retrieved object gets this pooL!

public class DaHibernateJpaPool implements DaPool {
	private EntityManager entityManager;
	private EntityManagerFactory entityManagerFactory;
	//Runtime exc -> Failure
	//checked exc -> multex exc - 
	
	public DaHibernateJpaPool() {
		try {
			this.entityManagerFactory = DaEmProvider.getInstance().getEntityManagerFactory();
			this.entityManager = entityManagerFactory.createEntityManager();
		} catch (Exception ex) {//create benutzen
			throw new Failure("Could not initialize JPA Entity Manager.", ex);
		}
	}
    
	@Override
	public void insert(DaObject io_object) {
		entityManager.persist(io_object);
	}

	@Override
	public boolean contains(DaObject io_object) {
		return entityManager.contains(io_object);
	}
	
	@Override
	public <E extends DaObject> E save(DaObject io_object) {
		io_object.attach(this);
		return (E) entityManager.merge(io_object);
	}

	@Override
	public boolean delete(DaObject io_object) {
		final boolean isPersistent = entityManager.contains(io_object);
		entityManager.remove(isPersistent ? io_object : entityManager.merge(io_object));
		return isPersistent;
	}

	@Override
	public <E extends DaObject> E find(Class<E> persistentClass, Long oid) {
		checkPersistentClass(persistentClass);
		E result = entityManager.find(persistentClass, oid);
		if (result == null) {
			//			throw create(DaOidNotFoundExc.class, i_oid);
		}
		result.attach(this);
		return (E) result;
	}

	@Override
	public <E extends DaObject> List<E> findAll(Class<E> persistentClass) {
		// TODO at try/catch
		final Query q = entityManager.createQuery("SELECT e FROM " + persistentClass.getSimpleName() + " e", persistentClass); 
		final List<E> results = q.getResultList();
		for (E item : results) {
			item.attach(this); //Test!
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
	 * Could not found entry for class "{0}" with field "{1}" and value "{2}".
	 */
	@SuppressWarnings("serial")
	public static final class DaFindOneByKeyExc extends multex.Failure {}
	
	public <E extends DaObject> E findOneByTwoKeys(Class<E> persistentClass, String firstKeyFieldName,
			Object firstKey, String secondKeyFieldName, Object secondKey) {
		final List<E> results = findManyByTwoKeys(persistentClass, firstKeyFieldName, firstKey, secondKeyFieldName, secondKey);
		final E result = results.get(0);
		result.attach(this);
		return result;
	}

	@Override
	public <E extends DaObject> List<E> findManyByKey(Class<E> persistentClass,
			String keyFieldName, Object keyFieldValue) {
		checkPersistentClass(persistentClass);
		final String className = persistentClass.getName();
		final Query q = entityManager.createQuery("select o from " + className + " o where " + keyFieldName + " = :keyValue ORDER BY o.oid DESC");
		q.setParameter("keyValue", keyFieldValue);
		final List<E> results = q.getResultList();
		for (E item : results) {
			item.attach(this); //Test!
		}
		return results;
	}
	
	public <E extends DaObject> List<E> findManyByTwoKeys(Class<E> persistentClass, String firstKeyFieldName,
			Object firstKey, String secondKeyFieldName, Object secondKey) {
		checkPersistentClass(persistentClass);
		final String className = persistentClass.getName();
		final  EntityManager entityManager = getEntityManager();
		
		final Query q = entityManager.createQuery("select o from " + className + " o where " + firstKeyFieldName + " = :firstKeyValue AND "
				+ secondKeyFieldName + " = :secondKeyValue");
		q.setParameter("firstKeyValue", firstKey);
		q.setParameter("secondKeyValue", secondKey);
		final List<E> results = q.getResultList();
		for (E item : results) {
			item.attach(this); //Test!
		}
		return results;
	}

	@Override
	public <E extends DaObject> List<E> findManyByQuery(Class<E> resultClass, 
			Class queryClass, String queryString, Object[] args) {
		if (!DaObject.class.isAssignableFrom(resultClass)) {
			//	throw create(DaNoPersistentClassExc.class, i_resultClass); 
		}
		if (wrongArgumentCount(queryString, args)) {
			//	throw create(DaArgumentCountExc.class, i_queryString, i_args.length);
		}
		String qlString = String.format(queryString, args);
		final Query query = entityManager.createQuery(qlString, resultClass);
		@SuppressWarnings("unchecked")
		final List<E> results = query.getResultList();
		for (E item : results) {
			item.attach(this); //Test!
		}
		return results; 
	}

	public <E extends DaObject> void checkPersistentClass(final Class<E> persistentClass) {
		if (!DaObject.class.isAssignableFrom(persistentClass)) {
			//throw create(DaNoPersistentClassExc.class, persistentClass); 
		}
	}

	private boolean wrongArgumentCount(String i_queryString, Object[] i_args) {
		return countOccurences(i_queryString, "%") != i_args.length;
	}

	private int countOccurences(String i_queryString, String pattern) {
		return i_queryString.length() - i_queryString.replace(pattern, "").length();
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	
}
