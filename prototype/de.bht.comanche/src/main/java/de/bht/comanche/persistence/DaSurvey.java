package de.bht.comanche.persistence;

import java.util.Collection;
import de.bht.comanche.logic.LgSurvey;

public interface DaSurvey {
    Collection<LgSurvey> findByName(String name);
}
