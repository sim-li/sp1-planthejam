package de.bht.comanche.rest;

public class ReResponseObject<E> {
	public static final int STATUS_OK = 200;
	
	public final E data;
	public final int responseCode;
	
	public ReResponseObject(E data, int responseCode) {
		this.data = data;
		this.responseCode = responseCode;
	}
	
	public boolean hasError() {
		return responseCode != STATUS_OK;
	}
}
