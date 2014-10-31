package de.bht.comanche.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
/**
 * @author Simon Lischka 
 * 
 * Initializes application specific persistence unit and 
 * generates pool.
 */
public class DaApplication {
	protected DaHibernateJpaPool pool;

	public DaApplication() {
		try {
			this.pool = new DaHibernateJpaPool();
		} catch (Exception ex) {
			throw new multex.Failure("Error initializing Hibernate / JPA entity manager. Check db configuration.", ex);
		}
	}

	public DaPool getPool() {
		return this.pool;
	}

	public void beginTransaction() {
		this.pool.getEntityManager().getTransaction().begin();
	}

	public void endTransaction(boolean success) {
		final EntityManager session = this.pool.getEntityManager();
		final EntityTransaction transaction = session.getTransaction();
		if (success) {
			transaction.commit();
		} else {
			transaction.rollback();
		} 
		session.close();
	}		
}
