package tda.src.view;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import tda.src.logic.TestData;
import tda.src.logic.TestRun;

public class TDATreeView {

	private TreeView<String> treeView;
	private final View view;

	public TDATreeView(View view) {
		this.view = view;

	}

	public TreeView<String> generateEmptyTreeView() {
		// Create a TreeView that has the selectedDirectory as rootItem
		TreeView<String> treeView = new TreeView<String>();
		this.treeView = treeView;
		return treeView;
	}

	public TreeView<String> fillTreeView(File selectedDirectory) {
		// Create a TreeView that has the selectedDirectory as rootItem
		treeView = createTreeView(selectedDirectory.toString());

		// Below works, but is deprecated
		// view.showTreeView(treeView);
		view.updateTreeView(treeView);

		return treeView;
	}

	public TreeItem<String> createTreeItems(String rootDirectory) {

		// Not sure if working on windows with this regex
		String[] rootFolder = rootDirectory.split("/|\\\\");

		TreeItem<String> rootItem = new TreeItem<String>(rootFolder[rootFolder.length - 1]);
		Path rootPath = Paths.get(rootDirectory);
		File[] files = rootPath.toFile().listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// Create new TreeItem as root with children
				TreeItem<String> subRoot = createTreeItems(file.toString());
				if (subRoot.getChildren().size() != 0) {
					rootItem.getChildren().add(subRoot);
				}
			} else {
				String lowerCaseFile = file.toString().toLowerCase();
				if (lowerCaseFile.endsWith(".xml") && lowerCaseFile.contains("testrun")) {
					// Create new TreeItem as leaf and add to rootItem
					TreeItem<String> treeItem = new TreeItem<String>(file.getName());
					rootItem.getChildren().add(treeItem);
				}
			}
		}
		return rootItem;
	}

	public TreeView<String> createTreeView(String rootDirectory) {

		EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
			handleMouseClicked(event);
		};

		treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);

		TreeItem<String> rootItem = createTreeItems(rootDirectory);
		treeView.setRoot(rootItem);
		return treeView;
	}

	public TreeView<String> getTreeView() {
		return treeView;
	}

	private void handleMouseClicked(MouseEvent event) {
		Node node = event.getPickResult().getIntersectedNode();
		// Accept clicks only on node cells, and not on empty spaces of the
		// TreeView
		if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
			String xmlName = (String) ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getValue();
			if (xmlName.endsWith(".xml") && event.getClickCount() == 2) {
				view.getController().handleTreeItemClick(xmlName);
			}
		}
	}

}
