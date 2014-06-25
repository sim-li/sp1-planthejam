package de.bht.comanche.logic;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity

public class DtTimeperiod {
	private Date startTime;
	private int durationInMinutes;
	
	public DtTimeperiod() {}
	
	public DtTimeperiod(Date startTime, int durationInMinutes) {
		this.startTime = startTime;
		this.durationInMinutes = durationInMinutes;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
    	return getId();
    }
    
	@OneToOne(mappedBy = "availability")
	
    public void setId(Long id) {
    	setId(id);
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
