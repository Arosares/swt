import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.logic.UnitTest;

public class TestedClassTests {
	
	private TestRun testRun1;
	private UnitTest unitTest1;
	private UnitTest unitTest2;
	private UnitTest unitTest4;
	private UnitTest unitTest3;
	private TestedClass testedClass;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testRun1 = new TestRun("Run1", "foo");
		unitTest1 = new UnitTest(testRun1, "test1", "fooTest1", "Run1fooTest1", "testFooBar");
		unitTest2 = new UnitTest(testRun1, "test2", "fooTest2", "Run1fooTest2", "testFooBar");
		unitTest3 = new UnitTest(testRun1, "test3", "fooTest3", "Run1fooTest3", "testFooBar");
		unitTest4 = new UnitTest(testRun1, "test4", "fooTest4", "Run1fooTest4", "testFooBar");
		
		testedClass = new TestedClass("TestMe", unitTest1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFailurePercentage50() {
		unitTest1.setOutcome("Passed");
		unitTest2.setOutcome("Passed");
		unitTest3.setOutcome("Failed");
		unitTest4.setOutcome("Failed");
		

		testedClass.addUnitTestToClassLog(unitTest2);
		testedClass.addUnitTestToClassLog(unitTest3);
		testedClass.addUnitTestToClassLog(unitTest4);
		
		
		assertEquals(50, testedClass.getFailurePercentageByTestrun(testRun1), 0.01);
	}
	
	@Test
	public void testFailurePercentage33() {
		unitTest1.setOutcome("Passed");
		unitTest2.setOutcome("Passed");
		unitTest3.setOutcome("Failed");
		testedClass.addUnitTestToClassLog(unitTest2);
		testedClass.addUnitTestToClassLog(unitTest3);
		
		assertEquals(33.33, testedClass.getFailurePercentageByTestrun(testRun1), 0.01);
	}
	
	@Test
	public void testFailurePercentage66() {
		unitTest1.setOutcome("Passed");
		unitTest2.setOutcome("Failed");
		unitTest3.setOutcome("Failed");
		testedClass.addUnitTestToClassLog(unitTest2);
		testedClass.addUnitTestToClassLog(unitTest3);
		
		assertEquals(66.66, testedClass.getFailurePercentageByTestrun(testRun1), 0.01);
	}
	
	@Test
	public void testFailurePercentage0() {
		unitTest1.setOutcome("Passed");
		unitTest2.setOutcome("Passed");
		unitTest3.setOutcome("Passed");
		testedClass.addUnitTestToClassLog(unitTest2);
		testedClass.addUnitTestToClassLog(unitTest3);
		
		assertEquals(0, testedClass.getFailurePercentageByTestrun(testRun1), 0.01);
	}
	
	@Test
	public void testGetClassnameOfTestMe() {
		assertEquals("TestMe", testedClass.getClassName());
	}
	
	@Test
	public void testSetClassnameToNewTestMe() {
		testedClass.setClassName("NewTestMe");
		assertEquals("NewTestMe", testedClass.getClassName());
	}
	
	@Test
	public void testAddNewUnitTestToClassLog() {
		List<UnitTest> before = testedClass.getClassLog().get(0).getUnitTestList();
		testedClass.addUnitTestToClassLog(unitTest2);
		before.add(unitTest2);
		
		assertEquals(before, testedClass.getUnitTestsByTestRun(testRun1));
	}
	
	@Test
	public void testAddExistingUnitTestToClassLog() {
		List<UnitTest> before = testedClass.getClassLog().get(0).getUnitTestList();
		testedClass.addUnitTestToClassLog(unitTest1);
		
		assertEquals(before, testedClass.getUnitTestsByTestRun(testRun1));
	}
	
	@Test
	public void testGetFailurePercentageByTestRun() {
		unitTest1.setOutcome("Passed");
		unitTest2.setOutcome("Passed");
		unitTest3.setOutcome("Failed");
		unitTest4.setOutcome("Failed");
		

		testedClass.addUnitTestToClassLog(unitTest2);
		testedClass.addUnitTestToClassLog(unitTest3);
		testedClass.addUnitTestToClassLog(unitTest4);
		
		double failurePercentage = testedClass.getClassLog().get(0).getFailurePercentage();
		
		assertEquals(failurePercentage, testedClass.getFailurePercentageByTestrun(testRun1), 0.01);
		List<UnitTest> before = testedClass.getClassLog().get(0).getUnitTestList();
		testedClass.addUnitTestToClassLog(unitTest1);
		
		assertEquals(before.toString(), testedClass.getClassLog().get(0).getUnitTestList().toString());
	}
	
	@Test
	public void testGetUnitTestsByTestRun() {
		List<UnitTest> compare = testedClass.getClassLog().get(0).getUnitTestList();		
		assertEquals(compare, testedClass.getUnitTestsByTestRun(testRun1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFailedGetUnitTestsByTestRun() {
		testedClass.getUnitTestsByTestRun(new TestRun("fail01", "FailRun"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFailedGetFailurePercentageByTestRun() {
		testedClass.getFailurePercentageByTestrun(new TestRun("fail01", "FailRun"));
	}
}
