package de.bht.comanche.persistence;

import java.util.Collection;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.server.exceptions.NoPersistentClassException;
import de.bht.comanche.server.exceptions.OidNotFoundException;
import javassist.NotFoundException;

public interface DaSurvey {
	void save(LgSurvey survey); // insert or update
	void delete(LgSurvey survey);
	LgSurvey find(long id) throws NotFoundException, NoPersistentClassException, OidNotFoundException;
	Collection<LgSurvey> findAll() throws NoPersistentClassException ;
    Collection<LgSurvey> findByName(String name);
}
