package tda.src.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import tda.src.logic.apriori.AprioriAnalyzer;

public class TestData {

	private static TestData testDataInstance;
	private ArrayList<TestRun> testRunList = new ArrayList<>();
	private ArrayList<UnitTest> unitTestList = new ArrayList<>();
	private TreeNode root = new TreeNode("/", null, null);
	private Analyzer analyzer = new AprioriAnalyzer();

	private TestData() {
	}

	// Singleton
	public static TestData getInstance() {
		if (TestData.testDataInstance == null) {
			TestData.testDataInstance = new TestData();
		}
		return TestData.testDataInstance;
	}

	// Getters for the lists
	public ArrayList<TestRun> getTestRunList() {
		return testRunList;
	}


	public ArrayList<UnitTest> getUnitTestList() {
		return unitTestList;
	}
	
	public List<TestedClass> getTestedClasses() {
		return root.getTestedClasses();
	}

	/**
	 * Checks if a pasted testrun is already in the files, and adds it if not.
	 * @param testRun
	 * @throws Exception
	 */
	public void addNewTestRun(TestRun testRun) throws Exception {
		boolean existing = false;
		// add class to list if not already there

		for (TestRun existingRun : testRunList) {
			if (testRun.getRunID().equals(existingRun.getRunID())) {

				testRun = existingRun;
				existing = true;
				// TODO: change to correct Exception
				throw new Exception("\nTestRun: " + testRun.getRunID() + " already loaded\n");
			}
		}
		if (!existing) {
			int index = 0;

			// Only for first TestRun
			if (testRunList.isEmpty()) {
				testRunList.add(testRun);
			} else {
				// Insert new TestRuns sorted by their StartTime
				for (TestRun existingRun : testRunList) {
					//If new TestRun is older than existing, increment Index-Counter
					if (testRun.getRunDate().compareTo(existingRun.getRunDate()) > 0) {
						index++;
						if (index == testRunList.size()) {
							testRunList.add(testRun);
							break;
						}
					} else {
						//If Index == List-Size, then just add new Run as last element
						if (index == testRunList.size()) {
							testRunList.add(testRun);
							break;
						} else {
						// Add new Run at the proper index in List
							testRunList.add(index, testRun);
							break;
						}
					}
				}
			}
		}
	}

	public void printTree() {
		System.out.println(root.printTree(0));
	}

	public void addNewTestedClassToTree(TestedClass newlyCreatedClass) {
		// Save copy for not working on the classes reference
		Queue<String> packageName = new LinkedList<String>();
		TestedClass existingClass = getClass(newlyCreatedClass);

		if (existingClass != null) {
			// classLog of new created Classes always have only one item
			UnitTestsToTestRunMapper newMapping = newlyCreatedClass.getClassLog().get(0);
			UnitTest unitTestOfNewClass = newMapping.getUnitTestList().get(0);
			existingClass.addUnitTestToClassLog(unitTestOfNewClass);
			

		} else {
			packageName.addAll(newlyCreatedClass.getPackageName());
			root.insert(packageName, newlyCreatedClass);
			
		}	
	}

	public void addNewUnitTest(UnitTest unitTest) {
		unitTestList.add(unitTest);
	}

	/**
	 * @param runID
	 * @return the TestRun according to the runID; returns null if TestRun
	 *         doesn't exist
	 */
	public TestRun getTestRunByID(String runID) {
		for (TestRun testRun : testRunList) {
			if (testRun.getRunID().equals(runID)) {
				return testRun;
			}
		}
		// TODO: Throw Exception
		return null;
	}
	
	public int getClassDistance(TestedClass classLeft, TestedClass classRight) {
		TreeNode leftNode = root.searchNode(classLeft.getClassName());
		TreeNode rightNode = root.searchNode(classRight.getClassName());
		
		return leftNode.getDistance(rightNode);
	}

	/**
	 * @param name
	 * @return the TestedClass according to the class name; returns null if
	 *         TestedClass doesn't exist
	 */
	public TestedClass getClassByName(String name) {
		return root.searchByName(name);
	}

	public TestedClass getClass(TestedClass testedClass) {
		return root.search(testedClass);
	}

	public TreeNode getRoot() {
		return root;
	}
	
	public int getTreeHeight() {
		return root.getDepth() - 2;
	}

	public AprioriAnalyzer getAnalyzer() {
//		System.out.println("TreeHeight is: " + getTreeHeight());
		return (AprioriAnalyzer) analyzer;
	}

}
