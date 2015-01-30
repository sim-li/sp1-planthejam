package de.bht.comanche.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A general data type for time periods, describing a start and end time.
 * 
 * It is used to describe the possible time periods of a survey or the
 * availability of users.
 * <p>
 * Seconds and milliseconds are not saved and automatically set to 0.
 * <p>
 * The class offers a <code>createEmptyTimePeriod()</code> method which creates a
 * default time period with duration -1. It should always be used when
 * expressing a non existent date, for example when a date could not
 * be determined. 
 * <p>
 * 
 * 
 * @author Simon Lischka
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

	public static final LgTimePeriod EMPTY_TIMEPERIOD = createEmptyTimePeriod();

	public LgTimePeriod() {
	}

	public static final LgTimePeriod createEmptyTimePeriod() {
		final LgTimePeriod emptyTimePeriod = new LgTimePeriod();
		emptyTimePeriod.setStartTime(new Date(0));
		emptyTimePeriod.setEndTime(new Date(-60000));
		return emptyTimePeriod;
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
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
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
