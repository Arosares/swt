package tda.src.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.scene.control.TreeItem;
import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.logic.TreeNode;
import tda.src.logic.apriori.StrongRule;
import tda.src.model.Model;
import tda.src.view.View;

public class Controller {

	final private Model model;
	final private View view;
	private TestedClass lastComparedClass;

	public Controller() {
		this.model = new Model();
		this.view = new View(this.model, this);

	}

	public void start() {
		// TODO implement this operation
		this.view.show();
	}

	/**
	 * Opens the Filebrowser inherent to the used Operating System. The user can
	 * select a single or multiple files to be loaded. Standard procedure would
	 * be loading entire folders (see below)
	 */
	public void openFile() {
		List<File> fileChoices = view.pathAlert();
		// Prevents OOB- and Nullpointer-Exceptions when aborting the file
		// selection
		if (fileChoices != null) {
			if (view.isInitiated() == false) {
				view.updateRootPane();
			}
			for (File xmlFile : fileChoices) {
				model.parseFile(xmlFile.toString());
			}

			TestRun testRun = TestData.getInstance().getTestRunList().get(0);
			TestData.getInstance().printTree();

			view.getTable().fillTestedClassTable(testRun);
			view.getTotals().showTestRunTotals(testRun);

			view.getClassTree().fillClassView(TestData.getInstance().getRoot());
		}

	}

	/**
	 * parseFilesInDirectory parses an entire Directory, including sub-folders
	 * 
	 * @param files
	 */
	private void parseFilesInDirectory(File[] files) {
		//TODO: Redundant Check to prevent Exceptions
		if (files.length != 0) {
			for (File file : files) {
				if (file.isDirectory()) {
					// Calls same method again.
					parseFilesInDirectory(file.listFiles());
				} else {
					String lowerCaseFile = file.toString().toLowerCase();
					if (lowerCaseFile.endsWith(".xml") && lowerCaseFile.contains("testrun")) {
						model.parseFile(file.toString());
					}

				}
			}
		}

	}

	/**
	 * @param rootDirectory
	 * @return
	 */
	public TreeItem<TestRun> createTreeItems(String rootDirectory) {

		// Not sure if working on windows with this regex
		String[] rootFolder = rootDirectory.split("/|\\\\");

		TreeItem rootItem = new TreeItem(rootFolder[rootFolder.length - 1]);
		Path rootPath = Paths.get(rootDirectory);
		File[] files = rootPath.toFile().listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// Create new TreeItem as root with children
				TreeItem subRoot = createTreeItems(file.toString());
				if (subRoot.getChildren().size() != 0) {
					rootItem.getChildren().add(subRoot);
				}
			} else {
				String lowerCaseFile = file.toString().toLowerCase();
				if (lowerCaseFile.endsWith(".xml") && lowerCaseFile.contains("testrun")) {
					// Create new TreeItem as leaf and add to rootItem
					TreeItem<TestRun> treeItem = new TreeItem(file.getName());
					rootItem.getChildren().add(treeItem);
				}
			}
		}
		return rootItem;
	}

	/**
	 * Opens the OSspecific File Browser to select a folder to be parsed.
	 */
	public void openFolder() {

		File selectedDirectory = this.view.directoryAlert();
		// Prevents Nullpointer- and OOB-Exceptions
		if (selectedDirectory != null) {
			if (view.isInitiated() == false) {
				view.updateRootPane();
			}
			// Parse all existing xml files in within the selectedDirectory
			File[] files = selectedDirectory.listFiles();
			parseFilesInDirectory(files);

			view.getTree().fillTreeView(selectedDirectory);

			view.getClassTree().fillClassView(TestData.getInstance().getRoot());

			TestData.getInstance().getAnalyzer().analyze();
			TestData.getInstance().getRoot().printTree(0);
		}
	}

	/**
	 * Calls the exit Alert Window. 
	 */
	public void exitMain() {
		this.view.exitAlert();
	}

	/**
	 * 
	 * @param testRun
	 * @return
	 */
	public List<TestedClass> getTestedClassesFromTestRun(TestRun testRun) {
		return model.getTestedClassesFromTestRun(testRun);
	}

	public void handleTreeItemClick(String xmlName) {
		List<TestRun> testRuns = TestData.getInstance().getTestRunList();
		handleResetGraph();
		for (TestRun testRun : testRuns) {
			if (testRun.getPath().contains(xmlName)) {
				view.getTable().fillTestedClassTable(testRun);
				view.getTotals().showTestRunTotals(testRun);
				break;
			}
		}
	}

	public void handleTableRowClick(TestedClass testedClass) {
		view.getGraph().setChartData(testedClass);
	}

	public void handleResetGraph() {
		view.getGraph().getLineChart().getData().clear();
	}

	public void handleClearDataButton() {
		view.getMenuBar().clearDataAlert();
	}

	/**
	 * Cleares all extracted Data parsed from the XML data. Should be updated
	 * with additional features added to the TDA.
	 */
	public void clearData() {
		// TODO: does not clear testruntotals yet

		// Clear the testData
		TestData.getInstance().getTestRunList().clear();
		TestData.getInstance().getUnitTestList().clear();

		// Delete the Table Data
		if (view.getTable().getData() != null) {
			view.getTable().getData().clear();
		}

		// Delete the TreeView
		view.getTree().getTreeView().setRoot(null);

		// Clear the classesTreeView
		view.updateClassView(view.getClassTree().generateEmptyClassView());

		// TODO Clear TestRunTotals

		// Clear the Graph Content by Calling the ResetButton Handler
		handleResetGraph();

		// Clear both Observable Lists for the TestRunInfos
		if (view.getTotals().getGeneratedList() != null) {
			// view.getTotals().getAllCounters().clear();
			view.getTotals().getGeneratedList().clear();
		}

	}

	public List<XYChart.Data<TestRun, Number>> calculateChartData(TestedClass testedClass) {
		List<XYChart.Data<TestRun, Number>> datas = new LinkedList<>();
		for (TestRun testRun : TestData.getInstance().getTestRunList()) {
			double yValue = testedClass.getFailurePercentageByTestrun(testRun);
			if (yValue == -1) {
				continue;
			}
			XYChart.Data<TestRun, Number> dataPoint = new XYChart.Data<TestRun, Number>(testRun, yValue);
			datas.add(dataPoint);
		}
		return datas;
	}

	public void handleClassTreeClick(TreeNode node) {
		if (node.getTestedClass() != null) {
			view.getGraph().setChartData(node.getTestedClass());
		}
	}
	public void handleContextMenuClick(TestRun testRun, TestedClass testedClass, boolean isSlot1) {
		System.out.println("Clicked node " + testRun.getRunName());
		
		if (lastComparedClass == null) {
			view.getComparison().updateComparisonSlot(testedClass, testRun, isSlot1);
			lastComparedClass = testedClass;
		} else if (testedClass.equals(lastComparedClass)) {
			view.getComparison().updateComparisonSlot(testedClass, testRun, isSlot1);
		} else {
			System.out.println("Yaw Dawg, don't compare different classes!");
			view.errorAlert("You cannot compare different classes with each other!\n Please select the same class");
			lastComparedClass = null;
			//TODO: Improve Handling of different class selection
		}
		
		
	}

	public void handleStrongRuleTableClick(StrongRule strongRule) {
		for (TestedClass testedClass : strongRule.getLeftSide()) {
			view.getGraph().setChartData(testedClass);
		}
		for (TestedClass testedClass : strongRule.getRightSide()) {
			view.getGraph().setChartData(testedClass);
		}
	}
	
}