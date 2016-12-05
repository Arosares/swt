package tda.src.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import tda.src.logic.TestData;
import tda.src.logic.TestRun;
import tda.src.logic.TestedClass;
import tda.src.model.Model;
import tda.src.view.View;

public class Controller {

	final private Model model;
	final private View view;

	public Controller() {
		this.model = new Model();
		this.view = new View(this.model, this);

	}

	public void start() {
		// TODO implement this operation
		this.view.show();
	}

	public void openFile() {
		List<File> fileChoices = view.pathAlert();
		if (fileChoices != null) {
			for (File xmlFile : fileChoices) {
				model.parseFile(xmlFile.toString());
			}
		}

		TestRun testRun = TestData.getInstance().getTestRunList().get(0);

		view.getTable().fillTestedClassTable(testRun);
		view.getTotals().showTestRunTotals(testRun, false);
	}

	private void parseFilesInDirectory(File[] files) {
		List<File> fileList = Arrays.asList(files);
		
		fileList.stream().parallel().forEach(file -> {
			if (file.isDirectory()) {
				// Calls same method again.
				parseFilesInDirectory(file.listFiles());
			} else {
				String lowerCaseFile = file.toString().toLowerCase();
				if (lowerCaseFile.endsWith(".xml") && lowerCaseFile.contains("testrun")) {
					model.parseFile(file.toString());
				}
			}
		});
	}

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

	public TreeView<TestRun> createTreeView(String rootDirectory) {

		TreeView<TestRun> treeView = new TreeView<TestRun>();
		TreeItem<TestRun> rootItem = createTreeItems(rootDirectory);
		treeView.setRoot(rootItem);

		return treeView;
	}

	public File openFolder() {

		File selectedDirectory = this.view.directoryAlert();
		if (selectedDirectory != null) {
			
			// Parse all existing xml files in within the selectedDirectory
			File[] files = selectedDirectory.listFiles();
			long millis = System.currentTimeMillis();
			parseFilesInDirectory(files);
			long finished = System.currentTimeMillis();
			
			System.out.println(finished - millis + " ms");
		}
		return selectedDirectory;
	}

	public void exitMain() {
		this.view.exitAlert();
	}

	public List<TestedClass> getTestedClassesFromTestRun(TestRun testRun) {
		return model.getTestedClassesFromTestRun(testRun);
	}

	public void handleTreeItemClick(String xmlName) {
		List<TestRun> testRuns = TestData.getInstance().getTestRunList();
		handleResetGraph();
		for (TestRun testRun : testRuns) {
			if (testRun.getPath().contains(xmlName)) {
				view.getTable().fillTestedClassTable(testRun);
				view.getTotals().showTestRunTotals(testRun, false);
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
		// Clear the testData
		TestData.getInstance().getTestRunList().clear();
		TestData.getInstance().getTestedClassList().clear();
		TestData.getInstance().getUnitTestList().clear();

		// Delete the Table Data
		if (view.getTable().getData() != null) {
			view.getTable().getData().clear();
		}

		// Delete the TreeView
		view.getTree().getTreeView().setRoot(null);

		// Clear the Graph Content by Calling the ResetButton Handler
		handleResetGraph();

		// Clear both Observable Lists for the TestRunInfos
		if (view.getTotals().getAllCounters() != null && view.getTotals().getGeneratedList() != null) {
			view.getTotals().getAllCounters().clear();
			view.getTotals().getGeneratedList().clear();
		}

	}

}