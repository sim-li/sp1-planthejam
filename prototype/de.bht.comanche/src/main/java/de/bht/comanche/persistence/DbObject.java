package de.bht.comanche.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DbObject {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	public DbObject() {}
	
	public Long getId() {
    	return id;
    }
}