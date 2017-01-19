import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.logic.TreeNode;
import tda.src.logic.UnitTest;

public class TreeNodeTests {
	private TestRun testRun1;
	private TestRun testRun2;

	private UnitTest[] unitTests = new UnitTest[10];
	private TreeNode root = new TreeNode("/", null, null);
	private TestedClass testedClass1;
	private TestedClass testedClass2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testRun1 = new TestRun("Run1", "run-name-1");
		testRun2 = new TestRun("Run2", "run-name-2");

		int half = unitTests.length / 2;

		for (int i = 0; i < half; i++) {
			unitTests[i] = new UnitTest(testRun1, "test" + i, "fooTest" + i, "Run1fooTest" + i, "testFooBar");
		}

		for (int i = half; i < unitTests.length; i++) {
			unitTests[i] = new UnitTest(testRun2, "test" + i, "fooTest" + i, "Run2fooTest" + i, "testFooBar");
		}

		testedClass1 = new TestedClass("Foo.Bar.hui", unitTests[0]);
		testedClass2 = new TestedClass("Foo.Bor.Argh", unitTests[9]);
		Queue<String> packageName1 = new LinkedList<String>();
		Queue<String> packageName2 = new LinkedList<String>();
		packageName1.addAll(testedClass1.getPackageName());
		packageName2.addAll(testedClass2.getPackageName());
		
		root.insert(packageName1, testedClass1);
		root.insert(packageName2, testedClass2);
		System.out.println(root.printTree(2));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDistanceFOUR() {
		
		TreeNode node1 = root.searchNode("hui");
		TreeNode node2 = root.searchNode("Argh");
		System.out.println(node1.printTree(0));
		System.out.println(node2.printTree(0));
		int distance = node1.getDistance(node2);

		assertEquals(4, distance);
	}

}
