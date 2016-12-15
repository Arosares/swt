import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.logic.TreeNode;
import tda.src.logic.UnitTest;

public class TreeNodeTests {
	
	private TreeNode root;
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
		root = new TreeNode("/", null, null);
		Queue<String> name = new LinkedList<>();
		Queue<String> name2 = new LinkedList<>();
		
		
	
		testRun1 = new TestRun("Run1", "run-name-1");
		testRun2 = new TestRun("Run2", "run-name-2");
		
		int half = unitTests.length/2;
		
		for (int i = 0; i < half; i++) {
			unitTests[i] = new UnitTest(testRun1, "test"+i, "fooTest"+i, "Run1fooTest"+i, "testFooBar");
		}
		
		for (int i = half; i < unitTests.length; i++) {
			unitTests[i] = new UnitTest(testRun2, "test"+i, "fooTest"+i, "Run2fooTest"+i, "testFooBar");
		}
		
		testedClass1 = new TestedClass("DMP.Test.argh.hui", unitTests[0]);
		TestedClass testedClass2 = new TestedClass("DMP.Test.ohja", unitTests[1]);
		name.addAll(testedClass1.getPackageName());
		name2.addAll(testedClass2.getPackageName());
		root.insert(name, testedClass1);
		root.insert(name2, testedClass2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		TreeNode hui = root.searchByName("hui");
		TreeNode ohja = root.searchByName("ohja");
		
		assertEquals(3, hui.calcDistance(ohja));
	}
	

}
