package tda.src.logic;

import java.util.ArrayList;

public class TestData {

	private static TestData testDataInstance;
	private ArrayList<TestedClass> testedClassList = new ArrayList<>();
	private ArrayList<TestRun> testRunList = new ArrayList<>();
	private ArrayList<UnitTest> unitTestList = new ArrayList<>();

	private TestData() {
	}

	// Singleton
	public static TestData getInstance() {
		if (TestData.testDataInstance == null) {
			TestData.testDataInstance = new TestData();
		}
		return TestData.testDataInstance;
	}

	// Getters for the lists
	public ArrayList<TestRun> getTestRunList() {
		return testRunList;
	}

	public ArrayList<TestedClass> getTestedClassList() {
		return testedClassList;
	}

	public ArrayList<UnitTest> getUnitTestList() {
		return unitTestList;
	}

	public void addNewTestRun(TestRun testRun) throws Exception {
		boolean existing = false;
		// add class to list if not already there

		for (TestRun existingRun : testRunList) {
			if (testRun.getRunID().equals(existingRun.getRunID())) {

				testRun = existingRun;
				existing = true;
				// TODO: change to correct Exception
				throw new Exception("\nTestRun: " + testRun.getRunID() + " already loaded\n");
			}
		}
		if (!existing) {
			testRunList.add(testRun);
		}
	}

	public void addNewTestedClass(TestedClass newlyCreatedClass) {
		TestedClass existingClass = getClassByName(newlyCreatedClass.getClassName());

		if (existingClass != null) {
			// classLog of new created Classes always have only one item
			UnitTestsToTestRunMapper newMapping = newlyCreatedClass.getClassLog().get(0);
			UnitTest unitTestOfNewClass = newMapping.getUnitTestList().get(0);
			existingClass.addUnitTestToClassLog(unitTestOfNewClass);
		} else {
			testedClassList.add(newlyCreatedClass);
		}
	}

	public void addNewUnitTest(UnitTest unitTest) {
		unitTestList.add(unitTest);
	}

	/**
	 * @param runID
	 * @return the TestRun according to the runID; returns null if TestRun
	 *         doesn't exist
	 */
	public TestRun getTestRunByID(String runID) {
		for (TestRun testRun : testRunList) {
			if (testRun.getRunID().equals(runID)) {
				return testRun;
			}
		}
		// System.err.println(runID + " is not a valid test run id.");
		// TODO: Throw Exception
		return null;
	}

	/**
	 * @param name
	 * @return the TestedClass according to the class name; returns null if
	 *         TestedClass doesn't exist
	 */
	public TestedClass getClassByName(String name) {
		for (TestedClass testedClass : testedClassList) {
			if (testedClass.getClassName().equals(name)) {
				return testedClass;
			}
		}
		// System.err.println(name + " is not a valid class name.");
		// TODO: Throw Exception
		return null;
	}
}
