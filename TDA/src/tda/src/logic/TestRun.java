package tda.src.logic;

import java.util.LinkedList;
import java.util.List;

public class TestRun {
	private String runID, runName, runUser;
	private Counters resultSummary;
	private List<TestedClass> testedClasses = new LinkedList<>();

	public String getRunID() {
		return runID;
	}

	public TestRun(String runID, String runName, String runUser) {
		super();
		this.runID = runID;
		this.runName = runName;
		this.runUser = runUser;
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
