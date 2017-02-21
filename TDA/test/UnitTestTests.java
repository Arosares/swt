import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tda.src.datastructure.TestRun;
import tda.src.datastructure.TestedClass;
import tda.src.datastructure.UnitTest;

public class UnitTestTests {
	
	private UnitTest unitTest; 
	private TestRun testRun; 
	
	@Before
	public void setUp() throws Exception {
		testRun = new TestRun("testRunID", "testRunName"); 
		unitTest = new UnitTest(testRun, "testID", "testName", "testExecutionID", "testMethodName");
	}
	
	@After 
	public void tearDown() throws Exception {
		testRun = null; 
		unitTest = null; 
	}
	
	@Test
	public void getUnitTestIDSuccess() {
		String testID = unitTest.getUnitTestID(); 
		assertEquals(testID, "testID"); 
	}

	@Test
	public void setUnitTestIDSuccess() {
		String newTestID = "newTestID"; 
		unitTest.setUnitTestID(newTestID);
		String changedTestID = unitTest.getUnitTestID(); 
		assertEquals(changedTestID, newTestID); 
	}

	@Test
	public void getUnitTestNameSuccess() {
		String testName = unitTest.getUnitTestName(); 
		assertEquals(testName, "testName"); 
	}

	@Test
	public void setUnitTestNameSuccess() {
		String newTestName = "newTestName"; 
		unitTest.setUnitTestName(newTestName);
		String changedTestName = unitTest.getUnitTestName(); 
		assertEquals(newTestName, changedTestName); 
	}

	@Test
	public void getUnitTestExecutionIDSuccess() {
		String testExecutionID = unitTest.getUnitTestExecutionID(); 
		assertEquals("testExecutionID", testExecutionID); 
	}

	@Test
	public void setUnitTestExecutionIDSuccess() {
		String newTestExecutionID = "newTestExecutionID"; 
		unitTest.setUnitTestExecutionID(newTestExecutionID);
		String changedTestExecutionID = unitTest.getUnitTestExecutionID(); 
		assertEquals(newTestExecutionID, changedTestExecutionID); 
	}

	@Test
	public void getTestMethodNameSuccess() {
		String testMethodName = unitTest.getTestMethodName(); 
		assertEquals(testMethodName, "testMethodName"); 
	}

	@Test
	public void setTestMethodNameSuccess() {
		String newTestMethodName = "newTestMethodName"; 
		unitTest.setTestMethodName(newTestMethodName);
		String changedTestMethodName = unitTest.getTestMethodName(); 
		assertEquals(newTestMethodName, changedTestMethodName); 
	}

	@Test
	public void getTestRunSuccess() {
		TestRun unitTestTestRun = unitTest.getTestRun(); 
		assertTrue(unitTestTestRun.equals(testRun)); 
	}

	@Test
	public void setTestRunSuccess() {
		TestRun newTestRun = new TestRun("newTestRunID", "newTestRunName");
		unitTest.setTestRun(newTestRun);
		TestRun changedTestRun = unitTest.getTestRun(); 
		assertTrue(newTestRun.equals(changedTestRun)); 
	}

	@Test
	public void getSetTestedClassSuccess() {
		TestedClass testedClass = new TestedClass("testedClass", unitTest); 
		unitTest.setTestedClass(testedClass); 
		TestedClass settedTestedClass = unitTest.getTestedClass(); 
		assertTrue(testedClass.equals(settedTestedClass)); 
		
	}

	@Test
	public void hasPassedsetOutcomePassedSuccess() {
		unitTest.setOutcome("Passed");
		boolean outcome = unitTest.hasPassed(); 
		assertTrue(outcome); 
	}

	@Test
	public void hasPassedSetOutcomeFailedSuccess() {
		unitTest.setOutcome("Failed");
		boolean outcome = unitTest.hasPassed(); 
		assertFalse(outcome); 
	}

	@Test
	public void toStringWithOutcomePassedSuccess() {
		unitTest.setOutcome("Passed");
		String string = unitTest.toString(); 
		assertEquals(("UnitTest [unitTestID=" + "testID" + ", unitTestName=" + "testName" + ", outcome=" + true + "]"), string);
	}
	
	@Test
	public void toStringWithOutcomeFailedSuccess() {
		unitTest.setOutcome("Failed");
		String string = unitTest.toString(); 
		assertEquals(("UnitTest [unitTestID=" + "testID" + ", unitTestName=" + "testName" + ", outcome=" + false + "]"), string);
	}

}
