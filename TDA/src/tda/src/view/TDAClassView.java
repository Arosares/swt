package tda.src.view;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import tda.src.datastructure.TreeNode;

/**
 * Implements the sidebar view "classes"
 *
 */
public class TDAClassView {

	private TreeView<TreeNode> classView;
	private final View view;

	public TDAClassView(View view) {
		this.view = view;

	}

	public TreeView<TreeNode> generateEmptyClassView() {
		// Create a TreeView that has the selectedDirectory as rootItem
		TreeView<TreeNode> treeView = new TreeView<TreeNode>();
		this.classView = treeView;
		return treeView;
	}

	public TreeView<TreeNode> createClassView(TreeNode node) {

		EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
			handleMouseClicked(event);
		};

		classView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);

		TreeItem<TreeNode> rootItem = createClassItems(node);
		classView.setRoot(rootItem);
		return classView;
	}

	public TreeItem<TreeNode> createClassItems(TreeNode node) {
		TreeItem<TreeNode> rootItem = new TreeItem<TreeNode>(node);
		rootItem.setExpanded(true);
		// All Leafs
		if (node.getChildren().size() == 0) {
			return rootItem;
		}

		for (TreeNode child : node.getChildren()) {
			// Create new TreeItem as root with children
			TreeItem<TreeNode> subRoot = createClassItems(child);
			rootItem.getChildren().add(subRoot);
		}
		return rootItem;
	}

	public TreeView<TreeNode> fillClassView(TreeNode node) {
		// Create a TreeView that has the selectedDirectory as rootItem
		classView = createClassView(node);

		// Below works, but is deprecated
		// view.showTreeView(treeView);
		view.updateClassView(classView);

		return classView;
	}

	private void handleMouseClicked(MouseEvent event) {
		Node node = event.getPickResult().getIntersectedNode();
		// Accept clicks only on node cells, and not on empty spaces of the
		// TreeView
		if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
			TreeNode clickedNode = (TreeNode) ((TreeItem) classView.getSelectionModel().getSelectedItem()).getValue();
				view.getController().handleClassTreeClick(clickedNode);
		}
	}

}
