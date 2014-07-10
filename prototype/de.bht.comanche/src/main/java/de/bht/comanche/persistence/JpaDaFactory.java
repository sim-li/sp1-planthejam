package de.bht.comanche.persistence;

import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;

public class JpaDaFactory implements DaFactory {

	@Override
	public DaUser getDaUser() {
		return new DaUserImpl(new PoolImpl<LgUser>());
	}
	
	@Override
	public DaSurvey getDaSurvey() {
		return new DaSurveyImpl(new PoolImpl<LgSurvey>());
	}
}
