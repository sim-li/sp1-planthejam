package de.bht.comanche.persistence;

import java.util.Collection;
import java.util.List;

import de.bht.comanche.exceptions.DaArgumentCountException;
import de.bht.comanche.exceptions.DaArgumentTypeException;
import de.bht.comanche.exceptions.DaNoPersistentClassException;
import de.bht.comanche.exceptions.DaNoQueryClassException;
import de.bht.comanche.exceptions.DaNotFoundException;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;

public class DaSurveyImpl extends DaGenericImpl<LgSurvey> implements DaSurvey {
	public DaSurveyImpl(DaPool pool) {
		super(LgSurvey.class, pool);
	}
	
	/** 
	 * FIXME Attention: Change id declaration in DbObject
	 * @throws DaArgumentTypeException 
	 * @throws DaArgumentCountException 
	 * @throws DaNoQueryClassException 
	 * @throws DaNoPersistentClassException 
	 */
	@Override
	public List<LgSurvey> findByName(String name) throws DaNotFoundException, DaNoPersistentClassException, DaNoQueryClassException, DaArgumentCountException, DaArgumentTypeException {
		return findByField("name", name);

	}

}
