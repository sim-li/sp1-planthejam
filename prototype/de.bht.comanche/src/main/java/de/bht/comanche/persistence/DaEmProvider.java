package de.bht.comanche.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DaEmProvider {
	private EntityManagerFactory emf;
    private static final DaEmProvider singleton = new DaEmProvider();
	public static final String persistenceUnitName = "planthejam.jpa";
	
	private DaEmProvider() {}
	
	public static DaEmProvider getInstance() {
		return singleton; 
	}
    
	public EntityManagerFactory getEntityManagerFactory() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(persistenceUnitName);
		} 
		return emf;
	}
	
	public void closeEntityManagerFactory() {
		if (emf.isOpen() && emf != null) {
			emf.close();
			emf = null;
		}
	}
}
