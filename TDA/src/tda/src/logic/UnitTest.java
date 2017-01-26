package tda.src.logic;

/**
 * Represents a unit test that tests a {@code TestedClass} in a {@code TestRun}.
 *
 */
public class UnitTest {

	private String unitTestID;
	private String unitTestName;
	private String unitTestExecutionID;
	private String testMethodName;
	private TestRun testRun;
	private TestedClass testedClass;
	private boolean passed;

	/**
	 * Constructor to instantiate an object of {@code UnitTest}.
	 * 
	 * @param testRun
	 *            - The {@code TestRun} in which this {@code UnitTest} is used.
	 * @param unitTestID
	 *            - The ID of this {@code UnitTest}.
	 * @param unitTestName
	 *            - The name of this {@code UnitTest}.
	 * @param unitTestExecutionID
	 *            - The ExecutionID of this {@code UnitTest}.
	 * @param testMethodName
	 *            - The name of the method this {@code UnitTest} tests.
	 */
	public UnitTest(TestRun testRun, String unitTestID, String unitTestName, String unitTestExecutionID,
			String testMethodName) {
		// TODO Auto-generated constructor stub
		this.testRun = testRun;
		this.unitTestID = unitTestID;
		this.unitTestName = unitTestName;
		this.unitTestExecutionID = unitTestExecutionID;
		this.testMethodName = testMethodName;

	}

	/**
	 * Returns the ID of this {@code UnitTest}.
	 * 
	 * @return {@code String} containing the ID.
	 */
	public String getUnitTestID() {
		return unitTestID;
	}

	/**
	 * Sets the ID of this {@code UnitTest} to the passed ID.
	 * 
	 * @param unitTestID
	 *            - The {@code String} containing the ID to be set.
	 */
	public void setUnitTestID(String unitTestID) {
		this.unitTestID = unitTestID;
	}

	/**
	 * Returns the name of this {@code UnitTest}.
	 * 
	 * @return {@code String} containing the name.
	 */
	public String getUnitTestName() {
		return unitTestName;
	}

	/**
	 * Sets the name of this {@code UnitTest} to the passed name.
	 * 
	 * @param unitTestName
	 *            - The {@code String} containing the name to be set.
	 */
	public void setUnitTestName(String unitTestName) {
		this.unitTestName = unitTestName;
	}

	/**
	 * Returns the ExecutionID of this {@code UnitTest}.
	 * 
	 * @return {@code String} containing the ExecutionID.
	 */
	public String getUnitTestExecutionID() {
		return unitTestExecutionID;
	}

	/**
	 * Sets the ExecutionID of this {@code UnitTest} to the passed ExecutionID.
	 * 
	 * @param unitTestExecutionID
	 *            - The {@code String} containing the ExecutionID to be set.
	 */
	public void setUnitTestExecutionID(String unitTestExecutionID) {
		this.unitTestExecutionID = unitTestExecutionID;
	}

	/**
	 * Returns the name of the method, tested in this {@code UnitTest}.
	 * 
	 * @return {@code String} containing the method name.
	 */
	public String getTestMethodName() {
		return testMethodName;
	}

	/**
	 * Sets the name of the method tested in this {@code UnitTest} to the passed
	 * method name.
	 * 
	 * @param testMethodName
	 *            - The {@code String} containing the method name to be set.
	 */
	public void setTestMethodName(String testMethodName) {
		this.testMethodName = testMethodName;
	}

	/**
	 * Returns the {@code TestRun} in which this {@code UnitTest} is executed.
	 * 
	 * @return {@code TestRun} of this {@code UnitTest}.
	 */
	public TestRun getTestRun() {
		return testRun;
	}

	/**
	 * Sets the {@code TestRun} of this {@code UnitTest} to the passed
	 * {@code TestRun}.
	 * 
	 * @param testRun
	 *            - The {@code TestRun} to be set.
	 */
	public void setTestRun(TestRun testRun) {
		this.testRun = testRun;
	}

	/**
	 * Returns the {@code TestedClass} to which this {@code UnitTest} belongs.
	 * 
	 * @return {@code TestedClass} of this {@code UnitTest}.
	 */
	public TestedClass getTestedClass() {
		return testedClass;
	}

	/**
	 * Sets the {@code TestedClass} of this {@code UnitTest} to the passed
	 * {@code TestedClass}.
	 * 
	 * @param testedClass
	 *            - The {@code TestedClass} to be set.
	 */
	public void setTestedClass(TestedClass testedClass) {
		this.testedClass = testedClass;
	}

	/**
	 * Returns the outcome of this {@code UnitTest}. If the {@code UnitTest} has
	 * passed, the outcome is {@code true}. Otherwise the outcome is
	 * {@code false}.
	 * 
	 * @return {@code boolean} outcome of this {@code UnitTest}.
	 */
	public boolean hasPassed() {
		return passed;
	}

	/**
	 * Sets the outcome of this {@code UnitTest}. If the passed {@code String}
	 * equals "Passed" the outcome will be set {@code true} otherwise
	 * {@code false}.
	 * 
	 * @param outcome
	 *            - The {@code String} containing the outcome to be set.
	 */
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
