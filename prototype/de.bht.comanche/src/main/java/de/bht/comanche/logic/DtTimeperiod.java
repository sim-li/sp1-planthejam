package de.bht.comanche.logic;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class DtTimeperiod {
	private Date startTime;
	private int durationInMinutes;
	private Long id;
	
	public DtTimeperiod() {}
	
	public DtTimeperiod(Date startTime, int durationInMinutes) {
		this.startTime = startTime;
		this.durationInMinutes = durationInMinutes;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
    	return this.id;
    }
	
	public void setId(Long id) {
    	this.id = id;
    }

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}
}
