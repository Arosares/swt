package tda.src.logic;

import java.util.ArrayList;

public class TestData {
	
	
//	Lists of already parsed Testruns
	ArrayList<String> testedClassList = new ArrayList<String>();
	
	ArrayList<String> testRunList = new ArrayList<String>();
	
	ArrayList<String> testList = new ArrayList<String>();
	
//	Checks the testedClassList for the currently parsed Class. Returns boolean.
	public boolean checkTestedClassListContent(String parsedClass) {
		return testedClassList.contains(parsedClass);
	}
//	Checks the testRunList for the Testrun currently being parsed. Returns Boolean.
	public boolean checkTestRunListContent(String parsedTestRunID){
		return testRunList.contains(parsedTestRunID.toString());
	}
//	Checks the TestList for the currently parsed Test. Returns boolean. High performance Impact.
	public boolean checkTestListContent(String parsedTest) {
		return testList.contains(parsedTest);
	}
	
	
//	Adds a new entry to the List with the name of a newly parsed Class.
	public boolean createInstanceOfTestedClass(String parsedClassName) {
//		TODO: new Instance currently temporary!
		TestedClass newTestedClass= new TestedClass(parsedClassName);
		testedClassList.add(parsedClassName);
		return true;
	}
	
//	Adds a new entry to the TestRunList with the ID of a new Test Run.
	public void addToTestRunList(String parsedTestRun) {
		testRunList.add(parsedTestRun);
	}
//	Add a newly found Test to the TestList.
	public void addToTestList(String parsedTest) {
		testRunList.add(parsedTest);
	}

	
	
}