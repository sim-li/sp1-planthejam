package de.bht.comanche.persistence;

import java.util.List;

import javassist.NotFoundException;

import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaPoolImpl.DaArgumentCountExc;
import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
import de.bht.comanche.persistence.DaPoolImpl.EntityExistsExc;
import de.bht.comanche.persistence.DaPoolImpl.IllegalArgumentExc;
import de.bht.comanche.persistence.DaPoolImpl.TransactionRequiredExc;

public interface DaUser {
	void save(LgUser user) throws EntityExistsExc, TransactionRequiredExc;
	void delete(LgUser user) throws TransactionRequiredException, IllegalArgumentException;
	LgUser find(long id) throws NotFoundException, DaNoPersistentClassExc, DaOidNotFoundExc;
    List <LgUser> findByName(String name) throws DaNoPersistentClassExc, /*DaNoQueryClassExc,*/ DaArgumentCountExc /*, DaArgumentTypeExc*/;
    void beginTransaction();
    void endTransaction(boolean success);
    public DaPool getPool();
    public void setPool(DaPool pool); 
    LgUser update(LgUser user) throws TransactionRequiredExc, IllegalArgumentExc;
}
