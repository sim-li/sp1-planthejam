package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;

public interface DaUser {
	LgUser save(LgUser user);
	void delete(LgUser user);
	LgUser update(LgUser user);
	LgUser find(long id) throws DaNoPersistentClassExc, DaOidNotFoundExc;
    List <LgUser> findByName(String name) throws DaNoPersistentClassExc;
    void beginTransaction();
    void endTransaction(boolean success);
    public DaPool getPool();
    public void setPool(DaPool pool); 
}
