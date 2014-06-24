package de.bht.comanche.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PoolRamblings {
	
	public static void main (String[] args) {
		
	}
	
    public void save(DbObject dbobject) {
    	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("planthejam.jpa");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
    	entityManager.persist(dbobject);
    }
}
