package de.bht.comanche.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.bht.comanche.persistence.DaFactory;
import de.bht.comanche.persistence.DaFactoryJpaImpl;

public class ReService {
	final DaFactory factory;
	
	public ReService() {
		factory = new DaFactoryJpaImpl();
	}
}

