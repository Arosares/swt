package tda.src.controller;

import java.io.File;
import java.io.IOException;
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
	private TestedClass compareSlot1;

	public Controller() {
		this.model = new Model();
		this.view = new View(this.model, this);

	}

	/**
	 * Loads the view
	 */
	public void start() {
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
		// TODO: Redundant Check to prevent Exceptions
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
	 * Generates a Tree showing strings of the names of all tested Classes, and
	 * the folders containing them.
	 * 
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
			
			startAnalyzer();
			
		}
	}
	public void startAnalyzer(){
		TestData.getInstance().getAnalyzer().analyze();
		TestData.getInstance().getRoot().printTree(0);
	}

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

	/**
	 * Handles clicks on our Testrun List, and calls the testrun Summary and
	 * Class Table.
	 * 
	 * @param xmlName
	 */
	public void handleTreeItemClick(String xmlName) {
		view.getMainWindowTabPane().getSelectionModel().select(view.getTableTab());
		List<TestRun> testRuns = TestData.getInstance().getTestRunList();
		handleResetGraph();
		handleResetComparison();
		for (TestRun testRun : testRuns) {
			if (testRun.getPath().contains(xmlName)) {
				view.getTable().fillTestedClassTable(testRun);
				view.getTotals().showTestRunTotals(testRun);
				break;
			}
		}
	}

	/**
	 * Loads a Class into the Linechart when doubleclicking on it in the
	 * "Table"-View
	 * 
	 * @param testedClass
	 */
	// TODO: Potentially Obsolete?
	public void handleTableRowClick(TestedClass testedClass) {
		view.getMainWindowTabPane().getSelectionModel().select(view.getChartTab());
		view.getGraph().setChartData(testedClass);
		System.out.println("WAT");
	}

	/**
	 * Clears the Linechart upon being called by the respective Button.
	 */
	public void handleResetGraph() {
		view.getGraph().getLineChart().getData().clear();
	}

	/**
	 * Handles the "Clear Data" Dialogue that opens when the user selects the
	 * appropriate Option in the dropdown-Menu.
	 */
	public void handleClearDataButton() {
		view.getMenuBar().clearDataAlert();
	}

	public void handleResetComparison() {
		view.getComparison().reset();
	}

	/**
	 * Clears all loaded Data from the system by calling clear-functions in
	 * TestData as well as erasing all visible lists and tables in the view. 
	 * The TestRun summary is cleared over a function in the view.
	 */
	public void clearData() {
		// Clear the testData
		TestData.getInstance().getTestRunList().clear();
		TestData.getInstance().getUnitTestList().clear();

		// Delete the Table Data
		if (view.getTable().getData() != null) {
			view.getTable().getData().clear();
		}

		// Delete the TreeView
		view.getTree().getTreeView().setRoot(null);

		// Calls the View funtion that replaces the Testrun Summary with an
		// empty one
		view.clearTotals();

		// Clear the classesTreeView
		view.updateClassView(view.getClassTree().generateEmptyClassView());

		// Clear the Graph Content by Calling the ResetButton Handler
		handleResetGraph();
		handleResetComparison();

		// Clear both Observable Lists for the TestRunInfos
		if (view.getTotals().getTestResults() != null) {
			view.getTotals().getIdLabel().setText("");
			view.getTotals().getTestResults().clear();
		}

		// Clear Apriori View
		if (view.getAnalyzer().getFrequentItems() != null && view.getAnalyzer().getStrongRuleItems() != null) {
			view.getAnalyzer().getFrequentItems().clear();
			view.getAnalyzer().getStrongRuleItems().clear();
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
			view.getMainWindowTabPane().getSelectionModel().select(view.getChartTab());
			view.getGraph().setChartData(node.getTestedClass());
		}
	}

	public void handleContextMenuClick(TestRun testRun, TestedClass testedClass, boolean isSlot1) {
		System.out.println("Clicked node " + testRun.getRunName());

		if (compareSlot1 == null) {
			view.getComparison().updateComparisonSlot(testedClass, testRun, isSlot1);
			compareSlot1 = testedClass;
		} else if (!isSlot1 && !testedClass.equals(compareSlot1)) {

			System.out.println("Yaw Dawg, don't compare different classes!");
			view.errorAlert("You cannot compare different classes with each other!\n Please select the same class");
		} else {
			view.getComparison().updateComparisonSlot(testedClass, testRun, isSlot1);
			compareSlot1 = testedClass;
		}

	}

	public void handleStrongRuleTableClick(StrongRule strongRule) {
		handleResetGraph();
		view.getMainWindowTabPane().getSelectionModel().select(view.getChartTab());

		for (TestedClass testedClass : strongRule.getLeftSide()) {
			view.getGraph().setChartData(testedClass);
		}
		for (TestedClass testedClass : strongRule.getRightSide()) {
			view.getGraph().setChartData(testedClass);
		}
	}

	public void handleAboutTDAClick() {
		view.getAboutTDAView().show();
	}

	public void handleTDAManualClick() {
		view.getManualView().show();
	}

	public String getManual() {
		// TODO Auto-generated method stub
		try {
			return model.getManual();
		} catch (IOException e) {
			e.printStackTrace();
			return "Manual could not be found";
		}
	}

	public String getLicence() {
		try {
			return model.getLicene();
		} catch (IOException e) {
			System.err.println("Licence.txt not found or readable\n" + e.getMessage());
			return "Licence could not be found";
		}
	}

}