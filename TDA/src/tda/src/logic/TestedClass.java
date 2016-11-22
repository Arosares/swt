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
		String runID = unitTest.getTestRun().getRunID();
		
		//Assume newTestRun
		boolean newTestRun = true;
		
		//Look for existing TestRuns
		for (UnitTestsToTestRunMapper runMapper : classLog) {
			if (runMapper.getTestRun().getRunID().equals(runID)) {
				newTestRun = false;
				//add UnitTest to existing TestRun
				runMapper.addUnitTestToTestRun(unitTest);
			}
		}
		//Create a new Mapping for a new TestRun
		if (newTestRun) {
			classLog.add(new UnitTestsToTestRunMapper(unitTest));
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
