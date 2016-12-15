package tda.src.logic;

import java.util.LinkedList;
import java.util.List;

public class TestRun {
	private String runID, runName;
	

	private Counters resultSummary;
	private List<TestedClass> testedClasses = new LinkedList<>();
	private String path;
	private String startTime;
	private TestRunStartTime runDate;

	

	public TestRun(String runID, String runName) {
		super();
		this.runID = runID;
		this.runName = runName;
	}

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
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
		setRunDate();
	}
	
	/**
	 * Extracts the Time the Test was Run from the startTime String
	 */
	private void setRunDate(){
		
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

	public void setTestedClasses(List<TestedClass> testedClasses) {
		this.testedClasses = testedClasses;
	}

	public List<TestedClass> getTestedClasses() {
		return this.testedClasses;
	}

	public void setResultSummary(Counters value) {
		this.resultSummary = value;
	}

	public Counters getResultSummary() {
		return this.resultSummary;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public TestRunStartTime getRunDate() {
		return runDate;
	}

	public void setRunDate(TestRunStartTime runDate) {
		this.runDate = runDate;
	}
	public String getRunID() {
		return runID;
	}
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
