package de.bht.comanche.logic;
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
 * be determined. EMPTY_TIMEPERIOD should only be used for comparison
 * and caused problems because of its global scope before.
 * <p>
 * 
 * @author Simon Lischka
 * @author Duc Tung Tong
 */

@Embeddable
public class LgTimePeriod {

	private static final long serialVersionUID = 1L;

	/**
	 * The <code>start time</code> of the described time period
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	/**
	 * The <code>end time</code> of the described time period
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	/**
	 * An empty time period 
	 */
	public static final LgTimePeriod EMPTY_TIMEPERIOD = createEmptyTimePeriod();

	/**
	 * Empty default constructor
	 */
	public LgTimePeriod() {}

	/**
	 * Copy constructor
	 * @param other A different time period
	 */
	public LgTimePeriod(final LgTimePeriod other) {
		this.startTime = other.startTime;
		this.endTime = other.endTime;
	}

	/**
	 * Creates an empty <code>time period</code> with a duration of
	 * -1 minutes. Use this to express a not existent <code>time period</code>.
	 * 
	 * @return A non existent time period
	 */
	public static final LgTimePeriod createEmptyTimePeriod() {
		final LgTimePeriod emptyTimePeriod = new LgTimePeriod();
		emptyTimePeriod.setStartTime(new Date(0));
		emptyTimePeriod.setEndTime(new Date(-60000));
		return emptyTimePeriod;
	}

	/**
	 * Determines whether this <code>time period</code> is empty.
	 * This is when it is equal to LgTimePeriod.EMPTY_TIMEPERIOD and 
	 * has a duration of -1.
	 * 
	 * @return True when time period is empty
	 */
	public boolean isNull() {
		return this.equals(EMPTY_TIMEPERIOD);
	}
	
	public Date getStartTime() {
		return this.startTime;
	}

	/**
	 * Set the start time. Cuts off seconds and milliseconds by setting
	 * them to 0.
	 * 
	 * @param startTime to be set
	 * @return This time period
	 */
	public LgTimePeriod setStartTime(final Date startTime) {
		this.startTime = cutOffSeconds(startTime);
		return this;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Set the end time. Cuts off seconds and milliseconds by setting
	 * them to 0.
	 * 
	 * @param endTime to be set
	 * @return This time period
	 */
	public LgTimePeriod setEndTime(Date endTime) {
		this.endTime = cutOffSeconds(endTime);
		return this;
	}

	/**
	 * Sets seconds and milliseconds of the given <code>time</code> to null
	 * and returns the result.
	 * 
	 * @param time A date with seconds and milliseconds
	 * @return A date with nulled out seconds and milliseconds
	 */
	private Date cutOffSeconds(final Date time) {
		final Calendar cal = Calendar.getInstance(); // locale-specific
		cal.setTime(time);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
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
}
