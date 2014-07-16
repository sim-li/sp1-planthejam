package de.bht.comanche.persistence;

import de.bht.comanche.rest.DaBase;

public interface DaFactory {
	DaBase getDaBase();
	DaUser getDaUser();
	DaInvite getDaInvite();
	DaSurvey getDaSurvey();
}
