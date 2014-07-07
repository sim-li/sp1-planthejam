package de.bht.comanche.persistence;

public class JpaDaFactory implements DaFactory {

	@Override
	public DaUser getDaUser() {
		return new DaUserImpl();
	}
	
	@Override
	public DaSurvey getDaSurvey() {
		return new DaSurveyImpl();
	}
}
