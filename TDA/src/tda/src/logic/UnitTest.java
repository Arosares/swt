package tda.src.logic;

import java.util.HashSet;
import java.util.Set;

public class UnitTest {

	String unitTestID;
	String unitTestName;
	String unitTestExecutionID;
	String testMethodName;
	TestRun testRun;
	TestedClass testedClass;

	public UnitTest(TestRun testRun, String unitTestID, String unitTestName, String unitTestExecutionID,
			String testMethodName) {
		// TODO Auto-generated constructor stub
		this.testRun = testRun;
		this.unitTestID = unitTestID;
		this.unitTestName = unitTestName;
		this.unitTestExecutionID = unitTestExecutionID;
		this.testMethodName = testMethodName;
		
	}

	private String testName;

	public void setTestName(String value) {
		this.testName = value;
	}

	public String getTestName() {
		return this.testName;
	}

	private String executionID;

	public void setExecutionID(String value) {
		this.executionID = value;
	}

	public String getExecutionID() {
		return this.executionID;
	}

	private boolean outcome;

	public void setOutcome(boolean value) {
		this.outcome = value;
	}

	public boolean getOutcome() {
		return this.outcome;
	}

	public TestedClass getTestedClass() {
		return testedClass;
	}

	public void setTestedClass(TestedClass testedClass) {
		this.testedClass = testedClass;
	}

	public TestRun getTestRun() {
		return testRun;
	}

	public void setTestRun(TestRun testRun) {
		this.testRun = testRun;
	}

	@Override
	public String toString() {
		return "UnitTest [unitTestID=" + unitTestID + ", unitTestName=" + unitTestName + ", outcome=" + outcome + "]";
	}
	
	
	
}
