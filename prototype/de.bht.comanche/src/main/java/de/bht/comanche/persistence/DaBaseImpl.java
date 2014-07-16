package de.bht.comanche.persistence;

import de.bht.comanche.logic.LgObject;
import de.bht.comanche.rest.DaBase;

public class DaBaseImpl extends DaGenericImpl<LgObject> implements DaBase {

	public DaBaseImpl(DaPool<LgObject> pool) {
		super(LgObject.class, pool);
	}
}
