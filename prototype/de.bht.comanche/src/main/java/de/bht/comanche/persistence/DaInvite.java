package de.bht.comanche.persistence;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

public interface DaInvite {
	void save(LgInvite invite) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException ;
	void delete(LgInvite invite)  throws TransactionRequiredException, IllegalArgumentException;
	LgInvite find(long id) throws NotFoundException, NoPersistentClassException, OidNotFoundException;
    void beginTransaction();
    void endTransaction(boolean success);
    public Pool getPool(); // Later overwritten by DaGenericImpl
    public void setPool(Pool pool); 
    public void flush();
}
