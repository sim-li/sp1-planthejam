package de.bht.comanche.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DaApplication {
	DaHibernateJpaPool pool;
	
	public DaApplication() {
		try {
			this.pool = new DaHibernateJpaPool();
		} catch (Exception ex) {
			throw new multex.Failure("Error initializing Hibernate / JPA", ex);
		}
	}
	
	public DaPool getPool() {
		return this.pool;
	}
	
	public void beginTransaction() {
		this.pool.getEntityManager().getTransaction().begin();
	}
	
	public void endTransaction(boolean success) {
		EntityManager session = this.pool.getEntityManager();
		EntityTransaction transaction = session.getTransaction();
		try {
			if (success) {
				transaction.commit();
			} else {
				transaction.rollback();
			}
		} finally {
			try {
				transaction.close();
			} catch (Exception ex) {
				multex.Msg.printReport(System.err, ex);
			}
		}
	}		
}
