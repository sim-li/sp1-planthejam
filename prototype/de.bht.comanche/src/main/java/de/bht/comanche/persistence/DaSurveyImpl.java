package de.bht.comanche.persistence;

import java.util.Collection;
import java.util.List;

import de.bht.comanche.logic.LgSurvey;

public class DaSurveyImpl extends DaGenericImpl<LgSurvey> implements DaSurvey {
	public DaSurveyImpl(Pool pool) {
		super(LgSurvey.class, pool);
	}

	@Override
	public List<LgSurvey> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
