package de.bht.comanche.persistence;

import java.util.Collection;

import de.bht.comanche.model.DmUser;

public class DaUserImpl extends DaGenericImpl<DmUser> implements DaUser {

	public DaUserImpl() {
		super(DmUser.class);
	}

	@Override
	public Collection<DmUser> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
