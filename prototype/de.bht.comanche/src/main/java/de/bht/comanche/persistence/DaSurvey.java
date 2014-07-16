package de.bht.comanche.persistence;

import java.util.List;

import javax.naming.NameNotFoundException;
import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import javassist.NotFoundException;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.exceptions.persistence.ArgumentCountException;
import de.bht.comanche.server.exceptions.persistence.ArgumentTypeException;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.NoQueryClassException;
import de.bht.comanche.server.exceptions.persistence.OidNotFoundException;

public interface DaSurvey {
	void save(LgSurvey survey) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException;
	void delete(LgSurvey survey) throws TransactionRequiredException, IllegalArgumentException;
	LgSurvey find(long id) throws NotFoundException, NoPersistentClassException, OidNotFoundException;
	List<LgSurvey> findAll() throws NoPersistentClassException ;
    List<LgSurvey> findByName(String name) throws NoPersistentClassException, NoQueryClassException, ArgumentCountException, ArgumentTypeException, de.bht.comanche.server.exceptions.persistence.NotFoundException;
    void beginTransaction();
    void endTransaction(boolean success);
    public Pool getPool(); // Later overwritten by DaGenericImpl
    public void setPool(Pool pool); 
}

