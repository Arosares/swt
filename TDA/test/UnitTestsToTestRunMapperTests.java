import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.logic.UnitTest;
import tda.src.logic.UnitTestsToTestRunMapper; 

public class UnitTestsToTestRunMapperTests {
	
	private UnitTestsToTestRunMapper mapper; 
	private UnitTest unitTest1;
	private UnitTest unitTest2;
	private UnitTest unitTest3;
	private UnitTest unitTest4; 
	private TestRun testRun; 
	
	@Before
	public void setUp() throws Exception {
		
		testRun = new TestRun("testRunID", "testRunName"); 
		unitTest1 = new UnitTest(testRun, "testID1", "testName1", "testExecutionID1", "testMethodName1");
		unitTest1.setOutcome("Passed");
		unitTest2 = new UnitTest(testRun, "testID2", "testName2", "testExecutionID2", "testMethodName2");
		unitTest2.setOutcome("Failed");
		unitTest3 = new UnitTest(testRun, "testID3", "testName3", "testExecutionID3", "testMethodName3");
		unitTest3.setOutcome("Passed");
		unitTest4 = new UnitTest(testRun, "testID4", "testName4", "testExecutionID4", "testMethodName4");
		unitTest4.setOutcome("Failed");
		mapper = new UnitTestsToTestRunMapper(unitTest1); 
	}
	
	@After 
	public void tearDown() throws Exception {
		mapper = null; 
		testRun = null; 
		unitTest1 = null;
		unitTest2 = null;
		unitTest3 = null;
	}
	
	@Test
	public void addUnitTestToTestRunSuccess() {
		mapper.addUnitTestToTestRun(unitTest2);
		List<UnitTest> compareList = new ArrayList<>();
		compareList.add(unitTest1); 
		compareList.add(unitTest2); 
		assertTrue(mapper.getUnitTestList().equals(compareList)); 
	}

	@Test
	public void getFailurePercentageSuccess() {
		mapper.addUnitTestToTestRun(unitTest2);
		mapper.addUnitTestToTestRun(unitTest3);
		mapper.addUnitTestToTestRun(unitTest4);
		double fp = mapper.getFailurePercentage(); 
		assertEquals(fp, 50.00, 0.0); 
	}

	@Test
	public void getUnitTestListSuccess() {
		List<UnitTest> compareList = new ArrayList<>();
		compareList.add(unitTest1); 
		assertTrue(mapper.getUnitTestList().equals(compareList)); 
	}

	@Test
	public void getTestRunSuccess() {
		TestRun compareTestRun = mapper.getTestRun(); 
		assertTrue(compareTestRun.equals(testRun)); 
	}

	@Test
	public void setTestRunSuccess() {
		TestRun newTestRun = new TestRun("newRunID", "newRunName");
		mapper.setTestRun(newTestRun);
		assertTrue(mapper.getTestRun().equals(newTestRun)); 
	}

	@Test
	public void setUnitTestListSuccess() {
		List<UnitTest> newList = new ArrayList<>();
		newList.add(unitTest2); 
		newList.add(unitTest3); 
		mapper.setUnitTestList(newList);
		assertTrue(mapper.getUnitTestList().equals(newList)); 
	}

	@Test
	public void toStringSuccess() {
		String string = mapper.toString(); 
		System.out.println(string);
		assertEquals(("Mapper [testRun=" + "testRunID" + ", unitTestList=" + 
				"[UnitTest [unitTestID=testID1, unitTestName=testName1, outcome=true]]" + 
				", failurePercentage=" + "0.0" + "]"), string);
	}

}
