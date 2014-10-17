package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.exceptions.DaNoPersistentClassException;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.persistence.DaPoolImpl.DaArgumentCountExc;
import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;
import de.bht.comanche.persistence.DaPoolImpl.DaNotFoundExc;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;
import de.bht.comanche.persistence.DaPoolImpl.EntityExistsExc;
import de.bht.comanche.persistence.DaPoolImpl.IllegalArgumentExc;
import de.bht.comanche.persistence.DaPoolImpl.TransactionRequiredExc;

public interface DaSurvey {
	void save(LgSurvey survey) throws EntityExistsExc, TransactionRequiredExc, IllegalArgumentExc;
	void delete(LgSurvey survey) throws TransactionRequiredExc, IllegalArgumentExc;
	LgSurvey find(long id) throws DaOidNotFoundExc;
	List<LgSurvey> findAll() throws DaNoPersistentClassException;
    List<LgSurvey> findByName(String name) throws DaNotFoundExc;
    void beginTransaction();
    void endTransaction(boolean success);
    public DaPool getPool(); // Later overwritten by DaGenericImpl
    public void setPool(DaPool pool); 
}

