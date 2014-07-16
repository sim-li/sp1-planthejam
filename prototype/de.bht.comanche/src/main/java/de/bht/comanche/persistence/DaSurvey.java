package de.bht.comanche.persistence;

import java.util.List;

import javax.naming.NameNotFoundException;
import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import javassist.NotFoundException;
import de.bht.comanche.exceptions.DaArgumentCountException;
import de.bht.comanche.exceptions.DaArgumentTypeException;
import de.bht.comanche.exceptions.DaNoPersistentClassException;
import de.bht.comanche.exceptions.DaNoQueryClassException;
import de.bht.comanche.exceptions.DaOidNotFoundException;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;

public interface DaSurvey {
	void save(LgSurvey survey) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException;
	void delete(LgSurvey survey) throws TransactionRequiredException, IllegalArgumentException;
	LgSurvey find(long id) throws NotFoundException, DaNoPersistentClassException, DaOidNotFoundException;
	List<LgSurvey> findAll() throws DaNoPersistentClassException ;
    List<LgSurvey> findByName(String name) throws DaNoPersistentClassException, DaNoQueryClassException, DaArgumentCountException, DaArgumentTypeException, de.bht.comanche.exceptions.DaNotFoundException;
    void beginTransaction();
    void endTransaction(boolean success);
    public DaPool getPool(); // Later overwritten by DaGenericImpl
    public void setPool(DaPool pool); 
}

