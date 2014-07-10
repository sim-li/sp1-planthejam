package de.bht.comanche.persistence;

import java.util.Collection;

import de.bht.comanche.logic.LgSurvey;

public class DaSurveyImpl extends DaGenericImpl<LgSurvey> implements DaSurvey {
	public DaSurveyImpl(Pool pool) {
		super(LgSurvey.class, pool);
	}

	@Override
	public void save(LgSurvey user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(LgSurvey user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<LgSurvey> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
