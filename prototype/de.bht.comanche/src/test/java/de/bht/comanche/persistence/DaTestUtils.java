package de.bht.comanche.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.bht.comanche.persistence.DaPool;

public class DaTestUtils {
	private EntityManager em;
	private EntityManagerFactory entityManagerFactory;
	final String persistenceUnitName;
	
	public DaTestUtils(DaPool pool) {
		persistenceUnitName = pool.getPersistenceUnitName();
	}
	
	/**
	 * Drops all tables by reinitializing the entity manager.
	 * CAREFUL: Resource consuming. Try cleaning up your tests manually whenever possible.
	 * Reinitializing may be advisable at the beginning of one unit test class file,
	 * but not at every single test.
	 * @throws DaException
	 */
	public void initializeDb() {
		Map<String, String> properties = new HashMap<String, String>(1);
		properties.put("hibernate.hbm2ddl.auto", "create");
		entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
	}
}
