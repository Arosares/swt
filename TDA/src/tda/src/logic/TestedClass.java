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

	public TestedClass(String className, UnitTest unitTest) {
		super();
		this.className = className;
		
		classLog.add(new UnitTestsToTestRunMapper(unitTest));
		
	}

	public void addUnitTestToClassLog(UnitTest unitTest) {
		System.err.println("Adding Test...");
		
		String runID = unitTest.getTestRun().getRunID();
		
		for (UnitTestsToTestRunMapper existingRun : classLog) {
			if (existingRun.getTestRun().getRunID().equals(runID)) {
				existingRun.addUnitTestToTestRun(unitTest);
				System.out.println("added test");
			} else {
				classLog.add(new UnitTestsToTestRunMapper(unitTest));
				System.out.println("New Run! created new mapper");
			}
		}
	}

	/*
	 * classLog:
	 * 
	 * [testRunID|failurePercentage|executionID|executionID|...]
	 * [testRunID|failurePercentage|executionID|...]
	 */
	private List<UnitTestsToTestRunMapper> classLog = new LinkedList<>();

	public List<UnitTestsToTestRunMapper> getClassLog() {
		return classLog;
	}

	public void setClassName(String value) {
		this.className = value;
	}

	public String getClassName() {
		return this.className;
	}

	public void addUnitTestToList(UnitTest unitTest) {

	}

	

	@Override
	public String toString() {
		return "Name: " + className;
	}

}
