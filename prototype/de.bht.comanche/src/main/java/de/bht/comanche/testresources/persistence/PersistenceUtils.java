package de.bht.comanche.testresources.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.bht.comanche.persistence.Pool;
import de.bht.comanche.server.exceptions.PersistenceException;

public class PersistenceUtils {
	private EntityManager em;
	private EntityManagerFactory entityManagerFactory;
	final String persistenceUnitName;
	
	public PersistenceUtils(Pool pool) {
		persistenceUnitName = pool.getPersistenceUnitName();
	}
	
	/**
	 * Drops all tables by reinitializing the entity manager.
	 * CAREFUL: Resource consuming. Try cleaning up your tests manually whenever possible.
	 * Reinitializing may be advisable at the beginning of one unit test class file,
	 * but not at every single test.
	 * @throws PersistenceException
	 */
	public void reinitizalizeEntityManager() throws PersistenceException {
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
	}
}
