package tda.src.logic;

import java.util.LinkedList;
import java.util.List;

public class TestedClass {
	/**
	 * <pre>
	 *           0..*     0..*
	 * TestedClass ------------------------- TestRun
	 *           testedClass        &lt;       testRun
	 * </pre>
	 */
	private String className;
	private double currentFailurePercentage = -1.00;

	/**
	 * classLog: testRunID|failurePercentage|List<UnitTest>
	 */
	private List<UnitTestsToTestRunMapper> classLog = new LinkedList<>();

	public TestedClass(String className, UnitTest unitTest) {
		super();
		this.className = className;

		classLog.add(new UnitTestsToTestRunMapper(unitTest));

	}

	public void addUnitTestToClassLog(UnitTest unitTest) {
		String runID = unitTest.getTestRun().getRunID();

		// Assume newTestRun
		boolean newTestRun = true;

		// Look for existing TestRuns
		for (UnitTestsToTestRunMapper runMapper : classLog) {
			if (runMapper.getTestRun().getRunID().equals(runID)) {
				newTestRun = false;
				// add UnitTest to existing TestRun
				runMapper.addUnitTestToTestRun(unitTest);
			}
		}
		// Create a new Mapping for a new TestRun
		if (newTestRun) {
			classLog.add(new UnitTestsToTestRunMapper(unitTest));
		}

	}

	public double getFailurePercentageByTestrun(TestRun testRun) {
		for (UnitTestsToTestRunMapper unitTestsToTestRunMapper : classLog) {
			if (unitTestsToTestRunMapper.getTestRun().getRunID().equals(testRun.getRunID())) {
				return unitTestsToTestRunMapper.getFailurePercentage();
			}
		}
		throw new IllegalArgumentException(testRun.getRunID() + " can't be found in the class log");
	}

	public List<UnitTest> getUnitTestsByTestRun(TestRun testrun) {
		for (UnitTestsToTestRunMapper unitTestsToTestRunMapper : classLog) {
			if (unitTestsToTestRunMapper.getTestRun().getRunID().equals(testrun.getRunID())) {
				return unitTestsToTestRunMapper.getUnitTestList();
			}
		}
		throw new IllegalArgumentException(testrun.getRunID() + " can't be found in the class log");
	}

	public List<UnitTestsToTestRunMapper> getClassLog() {
		return classLog;
	}

	public void setClassName(String value) {
		this.className = value;
	}

	public String getClassName() {
		return this.className;
	}

	public double getCurrentFailurePercentage() {
		return currentFailurePercentage;
	}

	public void setCurrentFailurePercentage(TestRun testrun) {
		currentFailurePercentage = getFailurePercentageByTestrun(testrun);
	}

	@Override
	public String toString() {
		return "Name: " + className;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
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
		TestedClass other = (TestedClass) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		return true;
	}

}
