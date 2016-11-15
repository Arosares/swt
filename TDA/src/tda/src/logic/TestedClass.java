package tda.src.logic;

import java.util.ArrayList;
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

}
