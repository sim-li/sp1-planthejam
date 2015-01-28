package de.bht.comanche.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A data type for time periods.
 * 
 * It is used to describe the possible time periods of a survey or the
 * availability of users.
 * <p>
 * Important note: This class needs to <strong>override hashCode and
 * equals</strong>, so that collections of LgTimePeriods are comparable. In the
 * current version timePeriods are considered equal when ...
 * 
 * @author Duc Tung Tong
 */

@Embeddable
public class LgTimePeriod {

	private static final long serialVersionUID = 1L;
	private final String DATE_PATTERN = "yyyy.MM.dd G HH:mm z";
	private final DateFormat df = new SimpleDateFormat(DATE_PATTERN);

	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	public static final LgTimePeriod EMPTY_TIMEPERIOD = new LgTimePeriod()
			.setStartTime(new Date(0)).setEndTime(new Date(-60000));

	public LgTimePeriod() {
	}

	public LgTimePeriod(final LgTimePeriod other) {
		this.startTime = other.startTime;
		this.endTime = other.endTime;
	}

	public boolean isNull() {
		return this.equals(EMPTY_TIMEPERIOD);
	}
	
	public Date getStartTime() {
		return this.startTime;
	}

	public LgTimePeriod setStartTime(final Date startTime) {
		this.startTime = startTime;
		return this;
	}

	public Date getEndTime() {
		return endTime;
	}

	public LgTimePeriod setEndTime(Date endTime) {
		this.endTime = endTime;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LgTimePeriod other = (LgTimePeriod) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!df.format(endTime).equals(df.format(other.endTime)))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!df.format(startTime).equals(df.format(other.startTime)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("LgTimePeriod [startTime=%s, endTime=%s]",
				startTime, endTime);
	}
}
