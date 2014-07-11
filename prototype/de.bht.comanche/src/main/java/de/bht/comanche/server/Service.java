package de.bht.comanche.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bht.comanche.persistence.DaFactory;
import de.bht.comanche.persistence.JpaDaFactory;

public class Service {
	final DaFactory factory;
	
	public Service() {
		factory = new JpaDaFactory();
	}
}
