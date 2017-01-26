package tda.src.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Represents a class, tested with {@code UnitTest} in a {@code TestRun}.
 *
 */
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

	/**
	 * Constructor to instantiate an object of {@code TestedClass}.
	 * 
	 * @param className
	 *            - The name of the {@code TestedClass}
	 * @param unitTest
	 *            - A {@code UnitTest} used to test this {@code TestedClass}
	 */
	public TestedClass(String className, UnitTest unitTest) {
		String[] packagePlusClassName = className.split("\\.");

		this.className = packagePlusClassName[packagePlusClassName.length - 1];

		for (int i = 0; i < packagePlusClassName.length - 1; i++) {
			packageName.add(packagePlusClassName[i]);
		}

		classLog.add(new UnitTestsToTestRunMapper(unitTest));

	}

	/**
	 * Adds a {@code UnitTest} to the {@code classLog} of this
	 * {@code TestedClass}.
	 * 
	 * @param unitTest
	 *            - The {@code UnitTest} to be added
	 */
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
	 * Returns the failure percentage of this {@code TestedClass} in the passed
	 * {@code TestRun}.
	 * 
	 * @param testRun
	 *            - The {@code TestRun} of which the failure percentage of this
	 *            {@code TestedClass} is returned.
	 * 
	 * @return {@code double} the failure percentage or -1 if the passed
	 *         {@code TestRun} didn't test this {@code TestedClass}.
	 */
	public double getFailurePercentageByTestrun(TestRun testRun) {
		for (UnitTestsToTestRunMapper unitTestsToTestRunMapper : classLog) {
			if (unitTestsToTestRunMapper.getTestRun().getRunID().equals(testRun.getRunID())) {
				return unitTestsToTestRunMapper.getFailurePercentage();
			}
		}
		return -1.0;
	}

	/**
	 * Returns a {@code List} of {@code UnitTest} that test this
	 * {@code TestedClass} in the passed {@code TestRun}.
	 * 
	 * @param testrun
	 *            - The {@code TestRun} of which a {@code List} of
	 *            {@code UnitTest} is returned.
	 * 
	 * @throws {@code
	 *             IllegalArgumentException} if the passed {@code TestRun} can
	 *             not be found.
	 * 
	 * @return {@code List} of {@code UnitTest}.
	 * 
	 * 
	 */
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
	

	/**
	 * Returns the classLog of this {@code TestedClass}.
	 * 
	 * @return {@code List} of {@code UnitTestsToTestRunMapper}
	 */
	public List<UnitTestsToTestRunMapper> getClassLog() {
		return classLog;
	}

	/**
	 * Sets the name of this {@code TestedClass}.
	 * 
	 * @param value
	 *            - A {@code String} containing the name to be set.
	 */
	public void setClassName(String value) {
		this.className = value;
	}

	/**
	 * Returns the name of this {@code TestedClass}.
	 * 
	 * @return {@code String} containing the name of this {@code TestedClass}
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * Returns the current failure percentage of this {@code TestedClass}.
	 * 
	 * @return {@code double} the current failure percentage
	 */
	public double getCurrentFailurePercentage() {
		return currentFailurePercentage;
	}

	/**
	 * Calls the method {@code getFailurePercentageByTestrun(TestRun testrun)}
	 * with the passed {@code TestRun} as parameter.
	 * 
	 * @param testrun
	 *            - The {@code TestRun} that is passed to
	 *            {@code getFailurePercentageByTestrun(TestRun testrun)} as
	 *            parameter.
	 */
	public void setCurrentFailurePercentage(TestRun testrun) {
		currentFailurePercentage = getFailurePercentageByTestrun(testrun);
	}

	/**
	 * Returns the package name of this {@code TestedClass}. 
	 * 
	 * @return {@code Queue} of {@code String} containing the package name. 
	 */
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
