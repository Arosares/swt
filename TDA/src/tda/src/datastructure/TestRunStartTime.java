package tda.src.datastructure;

/**
 * Represents the date on which a {@code TestRun} was started.
 *
 */
public class TestRunStartTime implements Comparable<TestRunStartTime> {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int min;
	private int sec;

	/**
	 * Constructor to instantiate an object of type {@code TestRunStartTime}.
	 * 
	 * @param year
	 *            - an {@code int} representing the year.
	 * @param month
	 *            - an {@code int} representing the month.
	 * @param day
	 *            - an {@code int} representing the day.
	 * @param hour
	 *            - an {@code int} representing the hour.
	 * @param min
	 *            - an {@code int} representing the minute.
	 * @param sec
	 *            - an {@code int} representing the second.
	 */
	public TestRunStartTime(int year, int month, int day, int hour, int min, int sec) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.min = min;
		this.sec = sec;
	}

	/**
	 * Returns the year of this {@code TestRunStartTime}.
	 * 
	 * @return an {@code int} representing the year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Returns the month of this {@code TestRunStartTime}.
	 * 
	 * @return an {@code int} representing the month.
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Returns the day of this {@code TestRunStartTime}.
	 * 
	 * @return an {@code int} representing the day.
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Returns the hour of this {@code TestRunStartTime}.
	 * 
	 * @return an {@code int} representing the hour.
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * Returns the minute of this {@code TestRunStartTime}.
	 * 
	 * @return an {@code int} representing the hour.
	 */
	public int getMin() {
		return min;
	}

	/**
	 * Returns the second of this {@code TestRunStartTime}.
	 * 
	 * @return an {@code int} representing the second.
	 */
	public int getSec() {
		return sec;
	}

	@Override
	public String toString() {
		return "TestRunStartTime [" + year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec + "]";
	}

	@Override
	public int compareTo(TestRunStartTime o) {
		int sizeComparison = Integer.compare(year, o.getYear());

		if (sizeComparison == 0) {
			sizeComparison = Integer.compare(month, o.getMonth());
			if (sizeComparison == 0) {
				sizeComparison = Integer.compare(day, o.getDay());
				if (sizeComparison == 0) {
					sizeComparison = Integer.compare(hour, o.getHour());
					if (sizeComparison == 0) {
						sizeComparison = Integer.compare(min, o.getMonth());
						if (sizeComparison == 0) {
							sizeComparison = Integer.compare(sec, o.getSec());
						}
					}
				}
			}
		}
		return sizeComparison;
	}
}
