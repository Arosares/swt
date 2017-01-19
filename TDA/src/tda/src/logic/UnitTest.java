package tda.src.logic;

public class UnitTest {

	private String unitTestID;
	private String unitTestName;
	private String unitTestExecutionID;
	private String testMethodName;
	private TestRun testRun;
	private TestedClass testedClass;
	private boolean passed;

	public UnitTest(TestRun testRun, String unitTestID, String unitTestName, String unitTestExecutionID,
			String testMethodName) {
		// TODO Auto-generated constructor stub
		this.testRun = testRun;
		this.unitTestID = unitTestID;
		this.unitTestName = unitTestName;
		this.unitTestExecutionID = unitTestExecutionID;
		this.testMethodName = testMethodName;

	}

	public String getUnitTestID() {
		return unitTestID;
	}

	public void setUnitTestID(String unitTestID) {
		this.unitTestID = unitTestID;
	}

	public String getUnitTestName() {
		return unitTestName;
	}

	public void setUnitTestName(String unitTestName) {
		this.unitTestName = unitTestName;
	}

	public String getUnitTestExecutionID() {
		return unitTestExecutionID;
	}

	public void setUnitTestExecutionID(String unitTestExecutionID) {
		this.unitTestExecutionID = unitTestExecutionID;
	}

	public String getTestMethodName() {
		return testMethodName;
	}

	public void setTestMethodName(String testMethodName) {
		this.testMethodName = testMethodName;
	}

	public TestRun getTestRun() {
		return testRun;
	}

	public void setTestRun(TestRun testRun) {
		this.testRun = testRun;
	}

	public TestedClass getTestedClass() {
		return testedClass;
	}

	public void setTestedClass(TestedClass testedClass) {
		this.testedClass = testedClass;
	}

	public boolean hasPassed() {
		return passed;
	}

	public void setOutcome(String outcome) {
		if (outcome.equals("Passed")) {
			this.passed = true;
		} else {
			this.passed = false;
		}
	}

	@Override
	public String toString() {
		return "UnitTest [unitTestID=" + unitTestID + ", unitTestName=" + unitTestName + ", outcome=" + passed + "]";
	}

}
