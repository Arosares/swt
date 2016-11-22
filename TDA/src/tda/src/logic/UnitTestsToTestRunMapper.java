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

	public UnitTestsToTestRunMapper(UnitTest unitTest) {
		this.testRun = unitTest.getTestRun();
		unitTestList.add(unitTest);
	}
	
	public void addUnitTestToTestRun(UnitTest unitTest){
		unitTestList.add(unitTest);
	}
	public int getFailurePercentage() {
		calculateFailurePercentage();
		return failurePercentage;
	}

	private void calculateFailurePercentage() {
		int numberOfUnitTests = unitTestList.size();
		int numberOfFailedTests = 0;
		for (UnitTest unitTest : unitTestList) {
			if (!unitTest.hasPassed()) {
				numberOfFailedTests++;
			}
		}
		failurePercentage = (numberOfFailedTests / numberOfUnitTests) * 100;
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

	@Override
	public String toString() {
		return "Mapper [testRun=" + testRun.getRunID() + ", unitTestList=" + unitTestList
				+ ", failurePercentage=" + getFailurePercentage() + "]";
	}
}
