package de.bht.comanche.persistence;

import de.bht.comanche.logic.DbObject;

public class DaBaseImpl extends DaGenericImpl<DbObject> implements DaBase {

	public DaBaseImpl(Pool<DbObject> pool) {
		super(DbObject.class, pool);
	}
}
