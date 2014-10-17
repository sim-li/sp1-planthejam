package de.bht.comanche.persistence;

import java.util.List;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.persistence.DaPoolImpl.DaNotFoundExc;

public class DaSurveyImpl extends DaGenericImpl<LgSurvey> implements DaSurvey {
	public DaSurveyImpl(DaPool pool) {
		super(LgSurvey.class, pool);
	}
	
	@Override
	public List<LgSurvey> findByName(String name) throws DaNotFoundExc {
		return findByField("name", name);

	}

}
