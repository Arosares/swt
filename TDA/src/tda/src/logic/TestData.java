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
				// change to correct Exception
				throw new Exception("\nTestRun: " + testRun.getRunID() + " already loaded\n");
			}
		}
		if (!existing) {
			testRunList.add(testRun);
		}
	}
	
	public void addNewTestedClass(TestedClass newlyCreatedClass) {
		boolean existing = false;
		// add class to list if not already there

		for (TestedClass existingClass : testedClassList) {
			
			if (newlyCreatedClass.getClassName().equals(existingClass.getClassName())) {
				UnitTestsToTestRunMapper newMapping = newlyCreatedClass.getClassLog().get(0);
				//classLog of new created Classes always have only one item
				UnitTest unitTestOfNewClass = newMapping.getUnitTestList().get(0);
				existingClass.addUnitTestToClassLog(unitTestOfNewClass);

				existing = true;
				break;
			}
		}
		if (!existing) {
			testedClassList.add(newlyCreatedClass);
		}
	}

	public void addNewUnitTest(UnitTest unitTest) {
		unitTestList.add(unitTest);
		
		
	}

}
