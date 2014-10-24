package de.bht.comanche.rest;

// TODO not necessary (?) to wrap the data for the response into an object -> refactor
public class ReResponseObject<E> {
	public final E data;
	
	public ReResponseObject(E data) {
		this.data = data;
	}
}
