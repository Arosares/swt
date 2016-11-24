package tda.src.controller;

import java.io.File;
import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
				model.parseFile(xmlFile.toPath());
			}
		}
	}

	public List<TestedClass> getTestedClassesFromTestRun(String runID) {
		return model.getTestedClassesFromTestRun(runID);
	}

	private void parseFilesInDirectory(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				parseFilesInDirectory(file.listFiles()); // Calls same method
															// again.
			} else {
				String lowerCaseFile = file.toString().toLowerCase();
				if (lowerCaseFile.endsWith(".xml") && lowerCaseFile.contains("testrun")) {
					model.parseFile(file.toPath());
				}

			}
		}

	}

	public TreeItem<File> createTreeItems(File rootDirectory) {

		TreeItem<File> rootItem = new TreeItem<File>(rootDirectory);

		File[] files = rootDirectory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// Create new TreeItem as root with children
				TreeItem<File> subRoot = createTreeItems(file);
				if (subRoot.getChildren().size() != 0) {
					rootItem.getChildren().add(subRoot);
				}
			} else {
				String lowerCaseFile = file.toString().toLowerCase();
				if (lowerCaseFile.endsWith(".xml") && lowerCaseFile.contains("testrun")) {
					// Create new TreeItem as leaf and add to rootItem
					rootItem.getChildren().add(new TreeItem(file.getName()));
				}
			}
		}
		return rootItem;
	}

	public TreeView<File> createTreeView(File rootDirectory) {

		TreeView<File> treeView = new TreeView<File>();
		TreeItem<File> rootItem = createTreeItems(rootDirectory);
		treeView.setRoot(rootItem);

		return treeView;
	}

	public void openFolder() {

		File selectedDirectory = this.view.directoryAlert();
		if (selectedDirectory != null) {

			// Parse all existing xml files in within the selectedDirectory
			File[] files = selectedDirectory.listFiles();
			parseFilesInDirectory(files);

			// Create a TreeView that has the selectedDirectory as rootItem
			TreeView<File> treeView = createTreeView(selectedDirectory);
			this.view.showTreeView(treeView);

		}
	}

	public void exitMain() {
		this.view.exitAlert();
	}

}