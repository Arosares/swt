package tda.src.datastructure;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a test run, i.e. the root of a parsed XML file.
 *
 */
public class TestRun {
	private String runID, runName;

	private Counters resultSummary;
	private List<TestedClass> testedClasses = new LinkedList<>();
	private String path;
	private String startTime;
	private TestRunStartTime runDate;

	/**
	 * Constructor to instantiate an object of type {@code TestRun}.
	 * 
	 * @param runID
	 *            - a {@code String} containing the ID of this {@code TestRun}.
	 * @param runName
	 *            - a {@code String} containing the name of this {@code TestRun}
	 *            .
	 */
	public TestRun(String runID, String runName) {
		super();
		this.runID = runID;
		this.runName = runName;
	}

	/**
	 * Adds the {@code TestedClass} that is passed as parameter to the
	 * {@code List} of {@code TestedClass} of this {@code TestRun}. If the
	 * passed {@code TestedClass} is already in the {@code List}, it will not be
	 * added.
	 * 
	 * @param testedClass
	 *            - the {@code TestedClass} to be added to this {@code TestRun}.
	 * 
	 */
	public void addTestedClassToTestRun(TestedClass testedClass) {
		boolean existing = false;
		for (TestedClass existingClass : testedClasses) {
			if (existingClass.getClassName().equals(testedClass.getClassName())) {
				existing = true;
			}
		}
		if (!existing) {
			testedClasses.add(testedClass);
		}
	}

	/**
	 * Sets the time at which this {@code TestRun} was started to the passed
	 * time.
	 * 
	 * @param startTime
	 *            - a {@code String} containing the time to be set.
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
		setRunDate();
	}

	/**
	 * Extracts the date the test run was started on from the {@code startTime}
	 * and sets the corresponding attribute of this {@code TestRun}.
	 * 
	 */
	private void setRunDate() {

		String[] startT = startTime.split("T");
		String[] date = startT[0].split("-");
		String[] timeWithJunk = startT[1].split("\\.");
		String[] time = timeWithJunk[0].split(":");

		int year = Integer.parseInt(date[0]);
		int month = Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[2]);

		int hrs = Integer.parseInt(time[0]);
		int min = Integer.parseInt(time[1]);
		int sec = Integer.parseInt(time[2]);

		runDate = new TestRunStartTime(year, month, day, hrs, min, sec);
	}

	/**
	 * Sets the {@code List} of {@code TestedClass} of this {@code TestRun} to
	 * the passed {@code List}.
	 * 
	 * @param testedClasses
	 *            - a {@code List} of {@code TestedClass}.
	 */
	public void setTestedClasses(List<TestedClass> testedClasses) {
		this.testedClasses = testedClasses;
	}

	/**
	 * Returns a {@code List} containing all {@code TestedClass} tested in this
	 * {@code TestRun}.
	 * 
	 * @return {@code List} of {@code TestedClass}.
	 */
	public List<TestedClass> getTestedClasses() {
		return this.testedClasses;
	}

	/**
	 * Sets the result summary of this {@code TestRun} to the passed value. The
	 * result summary contains all sums of the tests executed in this
	 * {@code TestRun}.
	 * 
	 * @param value
	 *            - a {@code Counters} object.
	 */
	public void setResultSummary(Counters value) {
		this.resultSummary = value;
	}

	/**
	 * Returns a {@code Counters} object, containing the result summary of this
	 * {@code TestRun}.
	 * 
	 * @return {@code Counters} with all totals of this {@code TestRun}.
	 */
	public Counters getResultSummary() {
		return this.resultSummary;
	}

	/**
	 * Returns the location of this {@code TestRun} in the file directory.
	 * 
	 * @return {@code String} containing the location of this {@code TestRun}.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the attribute of where this {@code TestRun} is located in the file
	 * directory to the passed path.
	 * 
	 * @param path
	 *            - a {@code String} containing the path to be set.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Returns the date on which this {@code TestRun} was started.
	 * 
	 * @return {@code TestRunStartTime} object, representing the start date.
	 */
	public TestRunStartTime getRunDate() {
		return runDate;
	}

	/**
	 * Sets the start date of this {@code TestRun} to the passed parameter.
	 * 
	 * @param runDate
	 *            - a {@code TestRunStartTime} object, representing the start
	 *            date to be set.
	 */
	public void setRunDate(TestRunStartTime runDate) {
		this.runDate = runDate;
	}

	/**
	 * Returns the ID of this {@code TestRun}.
	 * 
	 * @return a {@code String} containing the ID.
	 */
	public String getRunID() {
		return runID;
	}

	/**
	 * Returns the name of this {@code TestRun}.
	 * 
	 * @return a {@code String} containing the name of this {@code TestRun}.
	 */
	public String getRunName() {
		return runName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((runID == null) ? 0 : runID.hashCode());
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
		TestRun other = (TestRun) obj;
		if (runID == null) {
			if (other.runID != null)
				return false;
		} else if (!runID.equals(other.runID))
			return false;
		return true;
	}
}
