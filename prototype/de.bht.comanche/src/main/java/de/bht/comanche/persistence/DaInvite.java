package de.bht.comanche.persistence;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
import de.bht.comanche.persistence.DaPoolImpl.EntityExistsExc;
import de.bht.comanche.persistence.DaPoolImpl.IllegalArgumentExc;
import de.bht.comanche.persistence.DaPoolImpl.TransactionRequiredExc;

public interface DaInvite {
	void save(LgInvite invite) throws EntityExistsExc, TransactionRequiredExc, IllegalArgumentExc ;
	void delete(LgInvite invite)  throws TransactionRequiredExc, IllegalArgumentExc;
	LgInvite find(long id) throws DaOidNotFoundExc, DaNoPersistentClassExc, DaOidNotFoundExc;
    void beginTransaction();
    void endTransaction(boolean success);
    public DaPool getPool(); // Later overwritten by DaGenericImpl
    public void setPool(DaPool pool); 
}
