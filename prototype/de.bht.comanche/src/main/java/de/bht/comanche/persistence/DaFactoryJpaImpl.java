package de.bht.comanche.persistence;

import de.bht.comanche.logic.LgObject;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.rest.DaBase;

public class DaFactoryJpaImpl implements DaFactory {
	@Override
	public DaBase getDaBase() {
		return new DaBaseImpl(new DaPoolImpl<LgObject>());
	}
	
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
