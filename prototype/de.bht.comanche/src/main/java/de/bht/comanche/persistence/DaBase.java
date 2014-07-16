package de.bht.comanche.persistence;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.DbObject;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

public interface DaBase {
	void save(DbObject entity) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException ;
	void delete(DbObject entity)  throws TransactionRequiredException, IllegalArgumentException;
	DbObject find(long id) throws NotFoundException, NoPersistentClassException, OidNotFoundException;
    void beginTransaction();
    void endTransaction(boolean success);
    public Pool getPool(); // Later overwritten by DaGenericImpl
    public void setPool(Pool pool); 
}
