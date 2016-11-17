package tda.src.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps one TestedClass to One TestRun and multiple UnitTests
 *
 */
public class UnitTestsToTestRunMapper {
	private TestRun testRun;
	private List<UnitTest> unitTestList = new ArrayList<>();
	private int failurePercentage;

	public UnitTestsToTestRunMapper(TestRun testRun) {
		this.testRun = testRun;
	}
	
	public void mapUnitTestsToTestRun(UnitTest unitTest){
		unitTestList.add(unitTest);
	}
	public int getFailurePercentage() {
		return failurePercentage;
	}

	public List<UnitTest> getUnitTestList() {
		return unitTestList;
	}

	public TestRun getTestRun() {
		return testRun;
	}

	public void setTestRun(TestRun testRun) {
		this.testRun = testRun;
	}

	public void setUnitTestList(List<UnitTest> unitTestList) {
		this.unitTestList = unitTestList;
	}

	public void setFailurePercentage(int failurePercentage) {
		this.failurePercentage = failurePercentage;
	}

}
