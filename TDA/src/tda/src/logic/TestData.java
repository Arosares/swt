package tda.src.logic;

import java.util.ArrayList;

public class TestData {

	// Lists of already parsed Testruns
	
	private static TestData testDataInstance;
	
	private ArrayList<TestedClass> testedClassList = new ArrayList<>();

	private ArrayList<TestRun> testRunList = new ArrayList<>();

	private ArrayList<UnitTest> unitTestList = new ArrayList<>();
	
	private TestData() {
	}
	
	public static TestData getInstance () {
	    if (TestData.testDataInstance == null) {
	    	TestData.testDataInstance  = new TestData ();
	    }
	    return TestData.testDataInstance;
	  }

	// Lists of already parsed Testruns
	public ArrayList<TestRun> getTestRunList() {
		return testRunList;
	}
	
	public ArrayList<TestedClass> getTestedClassList() {
		return testedClassList;
	}

	public ArrayList<UnitTest> getUnitTestList() {
		return unitTestList;
	}

	// Checks the testedClassList for the currently parsed Class. Returns
	// boolean.
	public boolean checkTestedClassListContent(String parsedClass) {
		return testedClassList.contains(parsedClass);
	}

	// Checks the testRunList for the Testrun currently being parsed. Returns
	// Boolean.
	public boolean checkTestRunListContent(String parsedTestRunID) {
		return testRunList.contains(parsedTestRunID.toString());
	}

	// Checks the TestList for the currently parsed Test. Returns boolean. High
	// performance Impact.
	public boolean checkTestListContent(String parsedTest) {
		return unitTestList.contains(parsedTest);
	}

	// Adds a new entry to the List with the name of a newly parsed Class.
	public boolean createInstanceOfTestedClass(String parsedClassName) {
//		TODO: new Instance currently temporary!
		TestedClass newTestedClass= new TestedClass(parsedClassName);
		testedClassList.add(newTestedClass);

		return true;
	}
}
