package tda.src.logic;

public class TestRunStartTime implements Comparable<TestRunStartTime> {
	private int year;
	private int month;
	private int day;
	private int hour;
	private int min;
	private int sec;

	public TestRunStartTime(int year, int month, int day, int hour, int min, int sec) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.min = min;
		this.sec = sec;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMin() {
		return min;
	}

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
