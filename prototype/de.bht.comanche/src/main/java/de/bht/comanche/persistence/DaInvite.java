package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;

public interface DaInvite {
	LgInvite save(LgInvite invite);
	void delete(LgInvite invite);
	LgInvite update(LgInvite invite);
	LgInvite find(long id) throws DaNoPersistentClassExc, DaOidNotFoundExc;
	List<LgInvite> findAll() throws DaNoPersistentClassExc;
    void beginTransaction();
    void endTransaction(boolean success);
    public DaPool getPool(); // Later overwritten by DaGenericImpl
    public void setPool(DaPool pool);
}
