package de.bht.comanche.persistence;

import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;

public class DaFactoryJpaImpl implements DaFactory {
	@Override
	public DaUserImpl getDaUser() {
		return new DaUserImpl(new DaPoolImpl<LgUser>());
	}
	
	@Override
	public DaInvite getDaInvite() {
		return new  DaInviteImpl(new DaPoolImpl<LgInvite>());
	}

	@Override
	public DaSurvey getDaSurvey() {
		return new  DaSurveyImpl(new DaPoolImpl<LgSurvey>());
	}
}
