package de.bht.comanche.persistence;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.exceptions.DaNoPersistentClassException;
import de.bht.comanche.exceptions.DaOidNotFoundException;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgUser;

public interface DaInvite {
	void save(LgInvite invite) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException ;
	void delete(LgInvite invite)  throws TransactionRequiredException, IllegalArgumentException;
	LgInvite find(long id) throws NotFoundException, DaNoPersistentClassException, DaOidNotFoundException;
    void beginTransaction();
    void endTransaction(boolean success);
    public DaPool getPool(); // Later overwritten by DaGenericImpl
    public void setPool(DaPool pool); 
}
