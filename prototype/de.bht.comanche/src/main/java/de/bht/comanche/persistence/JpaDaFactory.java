package de.bht.comanche.persistence;

import de.bht.comanche.logic.DbObject;
import de.bht.comanche.logic.LgInvite;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.DaBase;

public class JpaDaFactory implements DaFactory {
	@Override
	public DaBase getDaBase() {
		return new DaBaseImpl(new PoolImpl<DbObject>());
	}
	
	@Override
	public DaUserImpl getDaUser() {
		return new DaUserImpl(new PoolImpl<LgUser>());
	}
	
	@Override
	public DaInvite getDaInvite() {
		return new  DaInviteImpl(new PoolImpl<LgInvite>());
	}


	@Override
	public DaSurvey getDaSurvey() {
		return new  DaSurveyImpl(new PoolImpl<LgSurvey>());
	}
}
