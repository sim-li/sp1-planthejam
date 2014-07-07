package de.bht.comanche.persistence;

import java.util.Collection;

import javassist.NotFoundException;

public interface DaSurvey {
	void save(LgSurvey survey); // insert or update
	void delete(LgSurvey survey);
	LgSurvey find(long id) throws NotFoundException;
	Collection<LgSurvey> findAll();
    Collection<LgSurvey> findByName(String name);
}
