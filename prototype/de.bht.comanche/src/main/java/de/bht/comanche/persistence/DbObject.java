package de.bht.comanche.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A marker interface.
 *
 */
@Entity
public abstract class DbObject {
    private Long id;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
    	return id;
    }

    private void setId(Long id) {
    	this.id = id;
    }
}
