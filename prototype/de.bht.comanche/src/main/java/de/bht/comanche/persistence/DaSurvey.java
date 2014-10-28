package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;
import de.bht.comanche.persistence.DaPoolImpl.DaOidNotFoundExc;

public interface DaSurvey {
    LgSurvey save(LgSurvey survey);
    void delete(LgSurvey survey);
    LgSurvey find(long id) throws DaNoPersistentClassExc, DaOidNotFoundExc;
    List<LgSurvey> findAll() throws DaNoPersistentClassExc;
    List<LgSurvey> findByName(String name) throws DaNoPersistentClassExc;
    void beginTransaction();
    void endTransaction(boolean success);
    public DaPool getPool(); // Later overwritten by DaGenericImpl
    public void setPool(DaPool pool);
}

