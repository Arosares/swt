import static org.junit.Assert.*;

import java.util.List;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.logic.UnitTest;

public class TestRunTests {
	
	private TestRun testRun1;
	private TestRun testRun2;
	
	private UnitTest[] unitTests = new UnitTest[10];
	
	private TestedClass testedClass1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testRun1 = new TestRun("Run1", "run-name-1", "fooUser");
		testRun2 = new TestRun("Run2", "run-name-2", "fooUser");
		
		int half = unitTests.length/2;
		
		for (int i = 0; i < half; i++) {
			unitTests[i] = new UnitTest(testRun1, "test"+i, "fooTest"+i, "Run1fooTest"+i, "testFooBar");
		}
		
		for (int i = half; i < unitTests.length; i++) {
			unitTests[i] = new UnitTest(testRun2, "test"+i, "fooTest"+i, "Run2fooTest"+i, "testFooBar");
		}
		
		testedClass1 = new TestedClass("TestMe", unitTests[0]);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testAddNewTestedClassToTestRun() {
		List<TestedClass> testedClasses = testRun1.getTestedClasses();
		testRun1.addTestedClassToTestRun(testedClass1);
		testedClasses.add(testedClass1);
		
		assertEquals(testedClasses, testRun1.getTestedClasses());
	}
	
	@Test
	public void testAddExistingTestedClassToTestRun() {
		testRun1.addTestedClassToTestRun(testedClass1);
		List<TestedClass> testedClasses = testRun1.getTestedClasses();
		
		// Adding class the second time should have no effect
		testRun1.addTestedClassToTestRun(testedClass1);
		
		assertEquals(testedClasses, testRun1.getTestedClasses());
	}
}
