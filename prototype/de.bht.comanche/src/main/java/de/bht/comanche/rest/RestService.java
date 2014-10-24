package de.bht.comanche.rest;

import de.bht.comanche.persistence.DaFactory;
import de.bht.comanche.persistence.DaFactoryJpaImpl;

public class RestService {
	final DaFactory factory;
	
	public RestService() {
		factory = new DaFactoryJpaImpl();
	}
}

