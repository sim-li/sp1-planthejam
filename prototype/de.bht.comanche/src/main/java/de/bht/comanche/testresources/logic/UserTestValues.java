package de.bht.comanche.testresources.logic;

public enum UserTestValues {
	NAME0 ("Alice"),
	TEL0 ("030/8914829"),
	EMAIL0 ("alice@bptankstelle.de"),
	PASSWORD0 ("yousnoozeyoulose"),
	
	NAME1 ("Bob"),
	TEL1 ("+001 32321 1231"),
	EMAIL1 ("bobby@surfersclub.de"),
	PASSWORD1 ("lasurfing");
	
	private final String s;
	
	private UserTestValues(final String s) {
		    this.s = s;
	}
	
	public String toString() {
		return s;
	}
}
