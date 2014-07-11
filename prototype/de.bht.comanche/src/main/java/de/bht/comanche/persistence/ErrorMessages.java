package de.bht.comanche.persistence;

public enum ErrorMessages {
	STRING_ONE("ONE"), 
	STRING_TWO("TWO");

	private final String text;

	private ErrorMessages(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
