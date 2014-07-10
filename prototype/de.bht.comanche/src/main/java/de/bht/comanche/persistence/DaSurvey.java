package de.bht.comanche.persistence;

import java.util.Collection;

import de.bht.comanche.logic.LgSurvey;
import javassist.NotFoundException;

public interface DaSurvey {
	void save(LgSurvey survey); // insert or update
	void delete(LgSurvey survey);
	LgSurvey find(long id) throws NotFoundException, NoPersistentClassExc, OidNotFoundExc;
	Collection<LgSurvey> findAll() throws NoPersistentClassExc ;
    Collection<LgSurvey> findByName(String name);
}
