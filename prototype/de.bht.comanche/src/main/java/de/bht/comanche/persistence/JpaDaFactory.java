package de.bht.comanche.persistence;

public class JpaDaFactory implements DaFactory {

	@Override
	public DaUser getUserDA() {
		return new DaUserImpl();
	}

}
