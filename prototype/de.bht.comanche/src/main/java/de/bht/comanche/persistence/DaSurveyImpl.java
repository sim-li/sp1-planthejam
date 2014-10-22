package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.persistence.DaPoolImpl.DaNoPersistentClassExc;

public class DaSurveyImpl extends DaGenericImpl<LgSurvey> implements DaSurvey {
	public DaSurveyImpl(DaPool pool) {
		super(LgSurvey.class, pool);
	}
	
	@Override
	public List<LgSurvey> findByName(String name) throws DaNoPersistentClassExc {
		return findByField("name", name);
	}

}
