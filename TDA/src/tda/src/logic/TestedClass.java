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
	 * classLog:
	 * testRunID|failurePercentage|List<UnitTest>
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
	
	public double getFailurePercentageByTestrun(TestRun testrun){
		for (UnitTestsToTestRunMapper unitTestsToTestRunMapper : classLog) {
			if(unitTestsToTestRunMapper.getTestRun().getRunID().equals(testrun.getRunID())){
				return unitTestsToTestRunMapper.getFailurePercentage();
			}
		}
		throw new IllegalArgumentException(testrun.getRunID() + " can't be found in the class log");
	}
	
	public List<UnitTest> getUnitTestsByTestRun(TestRun testrun){
		for (UnitTestsToTestRunMapper unitTestsToTestRunMapper : classLog) {
			if(unitTestsToTestRunMapper.getTestRun().getRunID().equals(testrun.getRunID())){
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

	@Override
	public String toString() {
		return "Name: " + className;
	}

	public double getCurrentFailurePercentage() {
		return currentFailurePercentage;
	}

	public void setCurrentFailurePercentage(TestRun testrun) {
		for (int i = 0; i < classLog.size(); i++) {
			if (getClassLog().get(i).getTestRun().getRunID().equals(testrun.getRunID())) {
				this.currentFailurePercentage = getClassLog().get(i).getFailurePercentage();
				break;
			}
		}
	}

}
