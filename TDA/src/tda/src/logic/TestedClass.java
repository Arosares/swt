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
	private List<UnitTest> unitTestList;

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
	public List<UnitTestsToTestRunMapper> classLog = new ArrayList<>();

	public void setClassName(String value) {
		this.className = value;
	}

	public String getClassName() {
		return this.className;
	}

	public void addUnitTestToList(UnitTest unitTest) {

	}

	public void addMapperToList(UnitTestsToTestRunMapper mapper) {
		boolean existing = false;
		
		for (UnitTestsToTestRunMapper mapperInLog : classLog) {
			if (mapperInLog.getTestRun().getRunID().equals(mapper.getTestRun().getRunID())) {
				existing = true;
			}
		}
		
		if (!existing) {
			classLog.add(mapper);
		}
	}

	@Override
	public String toString() {
		return "Name: " + className;
	}

}
