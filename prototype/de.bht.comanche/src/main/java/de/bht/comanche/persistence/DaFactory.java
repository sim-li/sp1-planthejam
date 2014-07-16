package de.bht.comanche.persistence;

import de.bht.comanche.server.DaBase;

public interface DaFactory {
	DaBase getDaBase();
	DaUser getDaUser();
	DaInvite getDaInvite();
	DaSurvey getDaSurvey();
}
