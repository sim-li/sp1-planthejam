package de.bht.comanche.logic;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class DtTimeperiod {
	private Date startTime;
	private int durationInMinutes;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
    	return getId();
    }
    
    public void setId(Long id) {
    	setId(id);
    }

	private Date getStartTime() {
		return startTime;
	}

	private void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	private int getDurationInMinutes() {
		return durationInMinutes;
	}

	private void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}
    
    
}
