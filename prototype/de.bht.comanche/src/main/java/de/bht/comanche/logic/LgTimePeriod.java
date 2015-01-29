package de.bht.comanche.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	public final Date EMPTY_START_TIME = new Date(0);
			
	public final Date EMPTY_END_TIME = new Date(-60000);

	public LgTimePeriod() {
//		this.startTime = this.EMPTY_START_TIME;
//		this.endTime = this.EMPTY_END_TIME;
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
		this.startTime = cutOffSeconds(startTime);
		return this;
	}

	private Date cutOffSeconds(final Date startTime) {
		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.setTime(startTime);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public Date getEndTime() {
		return endTime;
	}

	public LgTimePeriod setEndTime(Date endTime) {
		this.endTime = cutOffSeconds(endTime);
		return this;
	}

	@Override
	public String toString() {
		return String.format("LgTimePeriod [startTime=%s, endTime=%s]",
				startTime, endTime);
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
		} else if (!endTime.equals(other.endTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	public String formatWithNullCheck(Date date) {
		if (date == null) { 
			return null;
		}
		return df.format(date);
	}
	
}
