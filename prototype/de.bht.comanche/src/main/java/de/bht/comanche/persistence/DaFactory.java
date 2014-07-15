package de.bht.comanche.persistence;

public interface DaFactory {
	DaBase getDaBase();
	DaUser getDaUser();
	DaInvite getDaInvite();
//	DaSurvey getDaSurvey();
}
