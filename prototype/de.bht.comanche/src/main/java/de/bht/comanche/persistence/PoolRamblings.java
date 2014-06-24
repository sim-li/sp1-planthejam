package de.bht.comanche.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PoolRamblings {
	
	public static void main (String[] args) {
		PoolImpl impl = PoolImpl.getInstance();
		PoolImpl impl2 = PoolImpl.getInstance();
		impl.test();
		impl2.test();
	
	}
}
