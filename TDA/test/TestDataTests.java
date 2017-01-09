import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.logic.UnitTest;

public class TestDataTests {
	
	private TestData testData; 
	
	private TestRun testRun1;
	private TestRun testRun2;
	
	private UnitTest[] unitTests = new UnitTest[10];
	
	private TestedClass testedClass1; 
	private TestedClass testedClass2; 
	private TestedClass testedClass3; 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		testData = TestData.getInstance(); 
		
		testRun1 = new TestRun("Run1", "run-name-1");
		testRun2 = new TestRun("Run2", "run-name-2");
		
		int half = unitTests.length/2;
		
		for (int i = 0; i < half; i++) {
			unitTests[i] = new UnitTest(testRun1, "test"+i, "fooTest"+i, "Run1fooTest"+i, "testFooBar");
		}
		
		for (int i = half; i < unitTests.length; i++) {
			unitTests[i] = new UnitTest(testRun2, "test"+i, "fooTest"+i, "Run2fooTest"+i, "testFooBar");
		}
		
		testedClass1 = new TestedClass("TestMe", unitTests[0]);
		testedClass2 = new TestedClass("TestMe", unitTests[1]); 
		testedClass3 = new TestedClass("Test3", unitTests[2]); 
		
	}

	@After
	public void tearDown() throws Exception { 
		testData.getTestRunList().clear();
	}
	
	@Test 
	public void testAddNewTestRunSuccess(){
		try {
			testData.addNewTestRun(testRun1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(testData.getTestRunList().contains(testRun1)); 
	}
	
	@Test 
	public void testAddNewTestRunToAlreadyExistingTestRunListSuccess(){
		try {
			testData.addNewTestRun(testRun1);
			testData.addNewTestRun(testRun2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(testData.getTestRunList().contains(testRun2) && testData.getTestRunList().contains(testRun1)); 
	}
	
	@Test (expected = Exception.class)
	public void testAddNewTestRunFail() throws Exception{
			testData.addNewTestRun(testRun1);
			testData.addNewTestRun(testRun1);
	}
	
	@Test (expected = Exception.class)
	public void testAddNewTestRunToAlreadyExistingTestRunListFail() throws Exception{
			testData.addNewTestRun(testRun1);
			testData.addNewTestRun(testRun2);
			testData.addNewTestRun(testRun2);
	}
	
	@Test 
	public void testAddNewTestedClassSuccess(){
		testData.addNewTestedClassToTree(testedClass1); 
		
		assertTrue(testData.getClassByName("testedClass1").equals(testedClass1)); 
	}
	
	@Test 
	public void testAddNewTestedClassToAlreadyExistingTestedClassListSuccess(){
		try {
			testData.addNewTestedClassToTree(testedClass1); 
			testData.addNewTestedClassToTree(testedClass3); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(testData.getClassByName("testedClass3").equals(testedClass3) && 
				testData.getClassByName("testedClass1").equals(testedClass1)); 
	}
	
	@Test 
	public void testAddNewTestedClassExistingClass(){
		try {
			testData.addNewTestedClassToTree(testedClass1); 
			testData.addNewTestedClassToTree(testedClass2); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue((testData.getClassByName("testedClass1").getClassLog().get(0).getUnitTestList().get(0).equals(unitTests[0])) && 
				(testData.getClassByName("testedClass2").getClassLog().get(0).getUnitTestList().get(1).equals(unitTests[1]))); 
	}
	
	@Test 
	public void testAddNewUnitTest(){
		testData.addNewUnitTest(unitTests[0]);
		
		assertTrue(testData.getUnitTestList().contains(unitTests[0])); 
	}
	
	@Test 
	public void testGetTestRunByIDSuccess(){
		try {
			testData.addNewTestRun(testRun1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		TestRun addedTestRun = testData.getTestRunByID("Run1"); 
		
		assertTrue(addedTestRun == testRun1); 
	}
	
	@Test 
	public void testGetTestRunByIDFail(){
		try {
			testData.addNewTestRun(testRun1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		TestRun addedTestRun = testData.getTestRunByID("Run2"); 
		
		assertTrue(addedTestRun == null); 
	}
	
	@Test 
	public void testGetClassByName(){
		testData.addNewTestedClassToTree(testedClass1); 
		
		TestedClass addedClass = testData.getClassByName("TestMe"); 
		
		assertTrue(addedClass.equals(testedClass1)); 

	}

}























