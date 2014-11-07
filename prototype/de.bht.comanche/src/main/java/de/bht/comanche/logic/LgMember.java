package de.bht.comanche.logic;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import de.bht.comanche.persistence.DaObject;

public class LgMember extends DaObject{
	
	@NotNull
	@ManyToOne
	private LgGroup group;

}
