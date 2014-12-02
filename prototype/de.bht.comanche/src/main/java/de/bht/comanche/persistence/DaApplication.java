package de.bht.comanche.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * This class binds the DB specific layer, e.g. JPA/Hibernate, to the logic
 * layer by assigning a concrete implementation of <code>DaPool</code>.
 * 
 * <p>It has helper methods for transaction sandwiching (begin and end transaction
 * that have to be called on every series of DB-access operations). 
 * 
 * <p>Users should always access DB operations with <code>DaApplication</code> instead of
 * directly using a concrete implementation. When changing the persistence framework, the reference
 * should be updated here.
 * 
 * @author Simon Lischka
 * 
 */
public class DaApplication {
	
	/**
	 * Concrete DB layer implementation wrapped in <code>DaApplication</code>
	 */
	private final DaHibernateJpaPool pool;
	
	/**
	 * Failure message
	 * @TODO Replace with MulTex comment
	 */
	private final String DB_FAILURE_MSG = "Error initializing Hibernate / JPA entity manager. Check db configuration.";
	
	/**
	 * Constructs a new <code>DaApplication</code> by initializing the concrete
	 * DB layer implementation.
	 */
	public DaApplication() {
		try {
			this.pool = new DaHibernateJpaPool();
		} catch (Exception ex) {
			throw new multex.Failure(DB_FAILURE_MSG, ex);
		}
	}

	/**
	 * @return Initialized pool with basic operations for DB access.
	 */
	public DaPool getPool() {
		return this.pool;
	}

	/**
	 * Starts a resource transaction.
	 */
	public void beginTransaction() {
		this.pool.getEntityManager().getTransaction().begin();
	}

	/**
	 * Finalizes a resource transaction.
	 * 
	 * <p>The outcome of the operations sandwiched in the transaction
	 * should always be indicated with the <code>success</code> flag. 
	 * Wrong use can lead to unexpected behavior when persisting.
	 * 
	 * @param success
	 */
	public void endTransaction(final boolean success) {
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
