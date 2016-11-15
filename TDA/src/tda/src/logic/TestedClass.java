package tda.src.logic;

import java.util.ArrayList;
import java.util.List;

public class TestedClass implements Comparable<TestedClass> {
	/**
	 * <pre>
	 *           0..*     0..*
	 * TestedClass ------------------------- TestRun
	 *           testedClass        &lt;       testRun
	 * </pre>
	 */
	private String className;
	private List<UnitTest> unitTests;

	public TestedClass(String className) {
		super();
		this.className = className;
	}

	/*
	 * classLog:
	 * 
	 * [testRunID|failurePercentage|executionID|executionID|...]
	 * [testRunID|failurePercentage|executionID|...]
	 */
	public List<ArrayList<String>> classLog = new ArrayList<ArrayList<String>>();

	public void setClassName(String value) {
		this.className = value;
	}

	public String getClassName() {
		return this.className;
	}

	public void addUnitTestToList(UnitTest unitTest) {

	}

	public void addTestRunToClassLog(String testRunID, List<UnitTest> testList) {

		ArrayList<String> testRunToAdd = new ArrayList<String>();
		testRunToAdd.add(testRunID);
		testRunToAdd.add("dummy");

		int passedCounter = 0;
		for (UnitTest oneTest : testList) {
			testRunToAdd.add(oneTest.getExecutionID());
			if (oneTest.getOutcome()) {
				passedCounter++;
			}
		}
		testRunToAdd.set(1, Integer.toString(passedCounter / testList.size()));
		classLog.add(testRunToAdd);
	}

	@Override
	public int compareTo(TestedClass o) {
		// TODO Auto-generated method stub
		if (className.equals(o.getClassName())) {
			return 0;
		}
		return -1;

	}

	@Override
	public String toString() {
		return "Name: " + className;
	}

}
