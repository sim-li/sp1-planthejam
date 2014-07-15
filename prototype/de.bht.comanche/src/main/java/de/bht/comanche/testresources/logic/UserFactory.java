package de.bht.comanche.testresources.logic;

import de.bht.comanche.logic.LgUser;

public class UserFactory {
	public LgUser getUser0() {
		return (new LgUser()).
			setName(UserTestValues.NAME0.toString()).
				setEmail(UserTestValues.EMAIL0.toString()).
					setPassword(UserTestValues.PASSWORD0.toString()).
						setTel(UserTestValues.TEL0.toString());
	}
	public LgUser getUser1() {
		return (new LgUser()).
				setName(UserTestValues.NAME1.toString()).
					setEmail(UserTestValues.EMAIL1.toString()).
						setPassword(UserTestValues.PASSWORD1.toString()).
							setTel(UserTestValues.TEL1.toString());
	}
}
