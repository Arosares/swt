package tda.src.logic;

import java.util.LinkedList;
import java.util.List;

public class TestRun {
	private String runID, runName, runUser;
	private Counters resultSummary;

	public String getRunID() {
		return runID;
	}

	public TestRun(String runID, String runName, String runUser) {
		super();
		this.runID = runID;
		this.runName = runName;
		this.runUser = runUser;
	}

	private List<TestedClass> testedClasses = new LinkedList<>();

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

}
