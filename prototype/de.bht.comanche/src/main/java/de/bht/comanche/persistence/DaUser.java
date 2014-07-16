package de.bht.comanche.persistence;

import java.util.List;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.exceptions.DaArgumentCountException;
import de.bht.comanche.exceptions.DaArgumentTypeException;
import de.bht.comanche.exceptions.DaNoPersistentClassException;
import de.bht.comanche.exceptions.DaNoQueryClassException;
import de.bht.comanche.exceptions.DaOidNotFoundException;
import de.bht.comanche.logic.LgUser;

public interface DaUser {
	void save(LgUser user) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException ;
	void delete(LgUser user)  throws TransactionRequiredException, IllegalArgumentException;
	LgUser find(long id) throws NotFoundException, DaNoPersistentClassException, DaOidNotFoundException;
    List <LgUser> findByName(String name) throws DaNoPersistentClassException, DaNoQueryClassException, DaArgumentCountException, DaArgumentTypeException;
    void beginTransaction();
    void endTransaction(boolean success);
    public LgUser getDummy(); // for testing
    public DaPool getPool(); // Later overwritten by DaGenericImpl
    public void setPool(DaPool pool); 
    LgUser update(LgUser user) throws TransactionRequiredException, IllegalArgumentException;
}
