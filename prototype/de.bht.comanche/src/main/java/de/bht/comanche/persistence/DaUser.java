package de.bht.comanche.persistence;

import java.util.List;

import javassist.NotFoundException;

import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.exceptions.ArgumentCountException;
import de.bht.comanche.server.exceptions.ArgumentTypeException;
import de.bht.comanche.server.exceptions.NoPersistentClassException;
import de.bht.comanche.server.exceptions.NoQueryClassException;
import de.bht.comanche.server.exceptions.OidNotFoundException;

public interface DaUser {
	void save(LgUser user) throws EntityExistsException, TransactionRequiredException, IllegalArgumentException ;
	void delete(LgUser user)  throws TransactionRequiredException, IllegalArgumentException;
	LgUser find(long id) throws NotFoundException, NoPersistentClassException, OidNotFoundException;
    List <LgUser> findByName(String name) throws NoPersistentClassException, NoQueryClassException, ArgumentCountException, ArgumentTypeException;
    void beginTransaction();
    void endTransaction(boolean success);
    public LgUser getDummy(); // for testing
    public Pool<LgUser> getPool(); // Later overwritten by DaGenericImpl
}
