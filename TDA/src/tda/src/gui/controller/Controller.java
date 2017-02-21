package tda.src.gui.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.scene.control.TreeItem;
import tda.src.datastructure.TestData;
import tda.src.datastructure.TestRun;
import tda.src.datastructure.TestedClass;
import tda.src.datastructure.TreeNode;
import tda.src.gui.model.Model;
import tda.src.logic.analyzer.StrongRule;
import tda.src.view.View;

/**
 *  Controller of our MVC Pattern for the GUI
 *
 */
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

		return model.createTreeItems(rootDirectory);
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
			// The analyze-function was moved to it's own method to allow
			// calling it independently.
			startAnalyzer();

		}
	}

	/**
	 * Starts the Analyzer. Barring any code changes, this will always be the
	 * Apriori Analyzer.
	 */
	public void startAnalyzer() {
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
		handleResetChart();
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
	public void handleTableRowClick(TestedClass testedClass) {
		view.getMainWindowTabPane().getSelectionModel().select(view.getChartTab());
		view.getGraph().setChartData(testedClass);
	}

	/**
	 * Clears the Linechart upon being called by the respective Button.
	 */
	public void handleResetChart() {
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
	 * TestData as well as erasing all visible lists and tables in the view. The
	 * TestRun summary is cleared over a function in the view.
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
		handleResetChart();
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

	/**
	 * Grabs the Failure Percentage of Testruns from a single class and turns
	 * them into Chart Data
	 * 
	 * @param testedClass
	 * @return
	 */
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

	/**
	 * 
	 * @param node
	 */
	public void handleClassTreeClick(TreeNode node) {
		if (node.getTestedClass() != null) {
			view.getMainWindowTabPane().getSelectionModel().select(view.getChartTab());
			view.getGraph().setChartData(node.getTestedClass());
		}
	}

	/**
	 * Handles the Right-Click Context Menu for the Chart, and checks if the
	 * selected data point matches the class of an already chosen point. Throws
	 * an error alert popup if not.
	 * 
	 * @param testRun
	 * @param testedClass
	 * @param isSlot1
	 */
	public void handleContextMenuClick(TestRun testRun, TestedClass testedClass, boolean isSlot1) {
		if (compareSlot1 == null) {
			view.getComparison().updateComparisonSlot(testedClass, testRun, isSlot1);
			compareSlot1 = testedClass;
		} else if (!isSlot1 && !testedClass.equals(compareSlot1)) {
			view.errorAlert("You cannot compare different classes with each other!");
		} else {
			view.getComparison().updateComparisonSlot(testedClass, testRun, isSlot1);
			compareSlot1 = testedClass;
		}

	}

	/**
	 * Upon clicking on a generated strong Rule, shows all involved classes in
	 * the LineChart.
	 * 
	 * @param strongRule
	 */
	public void handleStrongRuleTableClick(StrongRule strongRule) {
		handleResetChart();
		view.getMainWindowTabPane().getSelectionModel().select(view.getChartTab());

		for (TestedClass testedClass : strongRule.getLeftSide()) {
			view.getGraph().setChartData(testedClass);
		}
		for (TestedClass testedClass : strongRule.getRightSide()) {
			view.getGraph().setChartData(testedClass);
		}
	}

	/**
	 * Tells the view to show the info window with the License.
	 */
	public void handleAboutTDAClick() {
		view.getAboutTDAView().show();
	}

	/**
	 * Tells the view to show the Manual.
	 */
	public void handleTDAManualClick() {
		view.getManualView().show();
	}

	/**
	 * Asks the model for the Manual and returns it. If no Manual can be found,
	 * prints a stacktrace and error message and returns a "could not be found"
	 * message instead.
	 * 
	 * @return
	 */
	public String getManual() {
		// TODO Auto-generated method stub
		try {
			return model.getManual();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("No Manual found\n" + e.getMessage());
			return "Manual could not be found";
		}
	}

	/**
	 * Calls upon the model to display the License for the user to view. If no
	 * License exists, it prints an error message and returns a
	 * "could not be found" String instead of the License.
	 * 
	 * @return
	 */
	public String getLicense() {
		try {
			return model.getLicense();
		} catch (IOException e) {
			System.err.println("License.txt not found or readable\n" + e.getMessage());
			return "License could not be found";
		}
	}

}