package tda.src.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TestedClass {
	/**
	 * <pre>
	 *           0..*     0..*
	 * TestedClass ------------------------- TestRun
	 *           testedClass        &lt;       testRun
	 * </pre>
	 */
	private String className;
	private Queue<String> packageName = new LinkedList<>();
	private double currentFailurePercentage = -1.00;

	/**
	 * classLog: testRunID|failurePercentage|List<UnitTest>
	 */
	private List<UnitTestsToTestRunMapper> classLog = new LinkedList<>();

	public TestedClass(String className, UnitTest unitTest) {
		String[] packagePlusClassName = className.split("\\.");
		
		this.className = packagePlusClassName[packagePlusClassName.length - 1];
		
		for (int i = 0; i < packagePlusClassName.length - 1; i++) {
			packageName.add(packagePlusClassName[i]);
		}
		
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
	
	
	/**
	 * 
	 * @param testRun
	 * @return the FP or -1 if the testRun didn't test a class
	 */
	public double getFailurePercentageByTestrun(TestRun testRun) {
		for (UnitTestsToTestRunMapper unitTestsToTestRunMapper : classLog) {
			if (unitTestsToTestRunMapper.getTestRun().getRunID().equals(testRun.getRunID())) {
				return unitTestsToTestRunMapper.getFailurePercentage();
			}
		}
		return -1.0;
	}

	public List<UnitTest> getUnitTestsByTestRun(TestRun testrun) {
		for (UnitTestsToTestRunMapper unitTestsToTestRunMapper : classLog) {
			if (unitTestsToTestRunMapper.getTestRun().getRunID().equals(testrun.getRunID())) {
				return unitTestsToTestRunMapper.getUnitTestList();
			}
		}
		throw new IllegalArgumentException(testrun.getRunID() + " can't be found in the class log");
	}
	
	public List<UnitTest> getUnitTestsByTestRun(TestRun testRun, boolean passed){
		List<UnitTest> unitTests = getUnitTestsByTestRun(testRun);
		List<UnitTest> returnList = new LinkedList<>();
		for (UnitTest unitTest : unitTests) {
			if (unitTest.hasPassed() == passed) {
				returnList.add(unitTest);
			}
		}
		return returnList;
	}
	
	public List<String> getUnitTestsNamesByTestRun(TestRun testRun, boolean passed){
		List<UnitTest> unitTests = getUnitTestsByTestRun(testRun);
		List<String> returnList = new LinkedList<>();
		for (UnitTest unitTest : unitTests) {
			if (unitTest.hasPassed() == passed) {
				returnList.add(unitTest.getUnitTestName());
			}
		}
		return returnList;
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

	public Queue<String> getPackageName() {
		return packageName;
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
