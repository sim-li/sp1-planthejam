package de.bht.comanche.persistence;

import java.util.Collection;
import java.util.List;
import de.bht.comanche.logic.LgSurvey;
import de.bht.comanche.logic.LgUser;
import de.bht.comanche.server.exceptions.persistence.ArgumentCountException;
import de.bht.comanche.server.exceptions.persistence.ArgumentTypeException;
import de.bht.comanche.server.exceptions.persistence.NoPersistentClassException;
import de.bht.comanche.server.exceptions.persistence.NoQueryClassException;

public class DaSurveyImpl extends DaGenericImpl<LgSurvey> implements DaSurvey {
	public DaSurveyImpl(Pool pool) {
		super(LgSurvey.class, pool);
	}
	
	/** 
	 * FIXME Attention: Change id declaration in DbObject
	 * @throws ArgumentTypeException 
	 * @throws ArgumentCountException 
	 * @throws NoQueryClassException 
	 * @throws NoPersistentClassException 
	 */
	@Override
	public List<LgSurvey> findByName(String name) throws NoPersistentClassException, NoQueryClassException, ArgumentCountException, ArgumentTypeException {
		return findByField("name", name);

	}

}
